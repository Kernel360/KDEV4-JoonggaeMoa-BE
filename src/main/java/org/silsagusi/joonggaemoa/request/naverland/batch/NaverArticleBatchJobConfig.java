package org.silsagusi.joonggaemoa.request.naverland.batch;

import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionScrapStatusRepository;
import org.silsagusi.joonggaemoa.request.naverland.service.NaverLandRequestService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NaverArticleBatchJobConfig {

	private static final String JOB_NAME = "naverArticleJob";
	private static final int CHUNK_SIZE = 20;

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final RegionScrapStatusRepository regionScrapStatusRepository;
	private final NaverLandRequestService naverLandRequestService;

	@Bean
	public Job naverArticleJob(Step naverArticleStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(naverArticleStep)
			.build();
	}

	@Bean
	@JobScope
	public Step naverArticleStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.<RegionScrapStatus, RegionScrapStatus>chunk(CHUNK_SIZE, transactionManager)
			.reader(scrapStatusReader())
			.processor(scrapStatusProcessor())
			.writer(scrapStatusWriter())
			.build();
	}

	@Bean
	@StepScope
	public ItemReader<RegionScrapStatus> scrapStatusReader() {
		return new IteratorItemReader<>(regionScrapStatusRepository.findTop50ByCompletedFalseOrderByIdAsc());
	}

	@Bean
	@StepScope
	public ItemProcessor<RegionScrapStatus, RegionScrapStatus> scrapStatusProcessor() {
		return scrapStatus -> {
			log.info("Fetching articles for region: {}", scrapStatus.getRegion().getCortarNo());
			naverLandRequestService.scrapArticles(scrapStatus);
			return scrapStatus;
		};
	}

	@Bean
	@StepScope
	public ItemWriter<RegionScrapStatus> scrapStatusWriter() {
		return regionScrapStatusRepository::saveAll;
	}
}

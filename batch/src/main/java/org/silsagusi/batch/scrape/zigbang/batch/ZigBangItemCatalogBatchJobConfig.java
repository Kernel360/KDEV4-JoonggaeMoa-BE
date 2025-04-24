package org.silsagusi.batch.scrape.zigbang.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.zigbang.service.ZigBangItemCatalogRequestService;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ZigBangItemCatalogBatchJobConfig {
	private static final String JOB_NAME = "zigBangItemCatalogJob";

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final ZigBangItemCatalogRequestService zigBangItemCatalogRequestService;

	@Bean
	public Job zigBangItemCatalogJob(Step zigBangItemCatalogStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(zigBangItemCatalogStep)
			.build();
	}

	@Bean
	@JobScope
	public Step zigBangItemCatalogStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<ScrapeStatus> regions = scrapeStatusRepository.findTop50ByCompletedFalseOrderByIdAsc();
				for (ScrapeStatus status : regions) {
					zigBangItemCatalogRequestService.scrapZigBangItemCatalogs(status);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

}

package org.silsagusi.batch.scrape.naverland.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.naverland.service.NaverLandRequestService;
import org.silsagusi.core.domain.article.Region;
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
public class NaverLandBatchJobConfig {

	private static final String JOB_NAME = "naverLandArticleJob";

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandRequestService naverLandRequestService;
	private final RegionRepository regionRepository;

	@Bean
	public Job naverLandArticleJob(
		Step initNaverLandScrapeStatusStep,
		Step naverLandArticleStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(initNaverLandScrapeStatusStep)
			.next(naverLandArticleStep)
			.build();
	}

	@Bean
	@JobScope
	public Step initNaverLandScrapeStatusStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<Region> regions = regionRepository.findAllByCortarType("sec");
				for (Region region : regions) {
					scrapeStatusRepository.upsertNative(
						region.getId(),
						1,
						false,
						null,
						"네이버부동산"
					);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	@Bean
	@JobScope
	public Step naverLandArticleStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<ScrapeStatus> regions = scrapeStatusRepository.findAll();
				for (ScrapeStatus status : regions) {
					naverLandRequestService.scrapNaverLand(status);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}
}

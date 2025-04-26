package org.silsagusi.batch.scrape.naverland.batch;

import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.naverland.service.NaverLandRequestService;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
public class NaverLandBatchJobConfig {

	private static final String JOB_NAME = "naverLandArticleJob";

	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandRequestService naverLandRequestService;
	private final RegionRepository regionRepository;

	public NaverLandBatchJobConfig(
		JobRegistry jobRegistry,
		JobRepository jobRepository,
		@Qualifier("metaTransactionManager")
		PlatformTransactionManager transactionManager,
		ScrapeStatusRepository scrapeStatusRepository,
		NaverLandRequestService naverLandRequestService,
		RegionRepository regionRepository) {
		this.jobRegistry = jobRegistry;
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.scrapeStatusRepository = scrapeStatusRepository;
		this.naverLandRequestService = naverLandRequestService;
		this.regionRepository = regionRepository;
	}

	@Bean
	public Job naverLandArticleJob(
		Step initNaverLandScrapeStatusStep,
		Step naverLandArticleStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(initNaverLandScrapeStatusStep)
			.next(naverLandArticleStep)
			.build();

		try {
			jobRegistry.register(new ReferenceJobFactory(job));
		} catch (Exception e) {
			throw new RuntimeException("Failed to register job", e);
		}

		return job;
	}

	@Bean
	public Step initNaverLandScrapeStatusStep() {
		return new StepBuilder("initNaverLandScrapeStatus", jobRepository)
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

package org.silsagusi.batch.scrape.naverland.batch;

import java.util.List;

import org.silsagusi.batch.infrastructure.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.naverland.service.NaverLandArticleRequestService;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class NaverLandArticleBatchJobConfig {

	private static final String JOB_NAME = "naverLandArticleJob";

	private final JobRepository jobRepository;
	private final JobRegistry jobRegistry;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandArticleRequestService naverLandArticleRequestService;

	public NaverLandArticleBatchJobConfig(
		JobRepository jobRepository,
		JobRegistry jobRegistry,
		@Qualifier("metaTransactionManager") PlatformTransactionManager transactionManager,
		ScrapeStatusRepository scrapeStatusRepository,
		NaverLandArticleRequestService naverLandArticleRequestService
	) {
		this.jobRepository = jobRepository;
		this.jobRegistry = jobRegistry;
		this.transactionManager = transactionManager;
		this.scrapeStatusRepository = scrapeStatusRepository;
		this.naverLandArticleRequestService = naverLandArticleRequestService;
	}

	@Bean
	public Job naverLandArticleJob(Step naverLandArticleStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(naverLandArticleStep)
			.build();

		try {
			jobRegistry.register(new ReferenceJobFactory(job));
		} catch (Exception e) {
			throw new RuntimeException("Failed to register job", e);
		}

		return job;
	}

	@Bean
	public Step naverLandArticleStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<ScrapeStatus> regions = scrapeStatusRepository.findTop50ByCompletedFalseOrderByIdAsc();
				for (ScrapeStatus status : regions) {
					naverLandArticleRequestService.scrapNaverArticles(status);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}
}

package org.silsagusi.batch.scrape.naverland.batch;

import java.time.LocalDateTime;

import org.silsagusi.batch.infrastructure.ScrapeStatusRepository;
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

@Configuration
public class NaverLandScrapeResetJobConfig {

	private static final String JOB_NAME = "scrapStatusResetJob";
	private final JobRepository jobRepository;
	private final JobRegistry jobRegistry;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;

	public NaverLandScrapeResetJobConfig(
		JobRepository jobRepository,
		JobRegistry jobRegistry,
		@Qualifier("metaTransactionManager") PlatformTransactionManager transactionManager,
		ScrapeStatusRepository scrapeStatusRepository
	) {
		this.jobRepository = jobRepository;
		this.jobRegistry = jobRegistry;
		this.transactionManager = transactionManager;
		this.scrapeStatusRepository = scrapeStatusRepository;
	}

	@Bean
	public Job scrapeStatusResetJob(Step scrapeStatusResetStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(scrapeStatusResetStep)
			.build();

		try {
			jobRegistry.register(new ReferenceJobFactory(job));
		} catch (Exception e) {
			throw new RuntimeException("Failed to register job", e);
		}

		return job;
	}

	@Bean
	public Step scrapeStatusResetStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet(((contribution, chunkContext) -> {
				LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
				scrapeStatusRepository.resetAllScrapStatus(cutoff);
				return RepeatStatus.FINISHED;
			}), transactionManager)
			.build();
	}
}

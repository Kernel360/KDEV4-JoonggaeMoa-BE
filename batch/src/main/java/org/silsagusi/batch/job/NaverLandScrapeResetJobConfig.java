package org.silsagusi.batch.job;

import java.time.LocalDateTime;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class NaverLandScrapeResetJobConfig {

	private static final String JOB_NAME = "naverLandScrapeStatusResetJob";

	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;

	@Bean
	public Job naverLandScrapeStatusResetJob(Step naverLandScrapeStatusResetStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(naverLandScrapeStatusResetStep)
			.build();

		// try {
		// 	jobRegistry.register(new ReferenceJobFactory(job));
		// } catch (Exception e) {
		// 	throw new RuntimeException("Failed to register job", e);
		// }

		return job;
	}

	@Bean
	public Step naverLandScrapeStatusResetStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet(((contribution, chunkContext) -> {
				LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
				// scrapeStatusRepository.resetAllScrapeStatus(cutoff);
				return RepeatStatus.FINISHED;
			}), transactionManager)
			.build();
	}
}

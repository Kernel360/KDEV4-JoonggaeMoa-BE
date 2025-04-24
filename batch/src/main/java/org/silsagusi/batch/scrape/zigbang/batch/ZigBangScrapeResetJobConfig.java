package org.silsagusi.batch.scrape.zigbang.batch;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
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

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class ZigBangScrapeResetJobConfig {

	private static final String JOB_NAME = "zigBangScrapeStatusResetJob";
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;

	@Bean
	public Job zigBangScrapeStatusResetJob(Step zigBangScrapeStatusResetStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(zigBangScrapeStatusResetStep)
			.build();
	}

	@Bean
	@JobScope
	public Step zigBangScrapeStatusResetStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet(((contribution, chunkContext) -> {
				LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
				scrapeStatusRepository.resetAllScrapeStatus(cutoff);
				return RepeatStatus.FINISHED;
			}), transactionManager)
			.build();
	}
}

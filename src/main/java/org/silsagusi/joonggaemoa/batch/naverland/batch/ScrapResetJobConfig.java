package org.silsagusi.joonggaemoa.batch.naverland.batch;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.core.domain.article.infrastructure.RegionScrapStatusRepository;
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

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ScrapResetJobConfig {

	private static final String JOB_NAME = "scrapStatusResetJob";
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final RegionScrapStatusRepository regionScrapStatusRepository;

	@Bean
	public Job scrapStatusResetJob(Step scrapStatusResetStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(scrapStatusResetStep)
			.build();
	}

	@Bean
	@JobScope
	public Step scrapStatusResetStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet(((contribution, chunkContext) -> {
				LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
				regionScrapStatusRepository.resetAllScrapStatus(cutoff);
				return RepeatStatus.FINISHED;
			}), transactionManager)
			.build();
	}
}

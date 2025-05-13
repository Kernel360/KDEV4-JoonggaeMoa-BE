package org.silsagusi.batch.job.naverland;

import org.silsagusi.batch.job.common.ArticleAddressStepConfig;
import org.silsagusi.batch.job.common.ScrapeStatusInitializeStepConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class NaverLandJobConfig {

	private final ScrapeStatusInitializeStepConfig scrapeStatusInitializeStepConfig;
	private final NaverLandComplexStepConfig naverLandComplexStepConfig;
	private final NaverLandArticleStepConfig naverLandArticleStepConfig;
	private final ArticleAddressStepConfig articleAddressStepConfig;

	private static final String NAVER_LAND_JOB = "naverLandJob";

	@Bean
	public Job naverLandJob(JobRepository jobRepository, JobExecutionListener jobExecutionListener) {
		return new JobBuilder(NAVER_LAND_JOB, jobRepository)
			.start(naverLandComplexStepConfig.naverLandComplexStep())
			.next(naverLandArticleStepConfig.naverLandArticleStep())
			.next(articleAddressStepConfig.articleAddressStep())
			.next(scrapeStatusInitializeStepConfig.naverLandScrapeStatusInitializeStep())
			.listener(jobExecutionListener)
			.build();
	}
}

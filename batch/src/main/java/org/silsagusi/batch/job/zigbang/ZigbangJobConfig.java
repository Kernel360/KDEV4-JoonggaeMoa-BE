package org.silsagusi.batch.job.zigbang;

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
public class ZigbangJobConfig {

	private final ScrapeStatusInitializeStepConfig scrapeStatusInitializeStepConfig;
	private final ZigbangComplexStepConfig zigbangComplexStepConfig;
	private final ZigbangArticleStepConfig zigbangArticleStepConfig;
	private final ArticleAddressStepConfig articleAddressStepConfig;

	private static final String ZIGBANG_JOB = "zigbangJob";

	@Bean
	public Job zigbangJob(JobRepository jobRepository, JobExecutionListener jobExecutionListener) {
		return new JobBuilder(ZIGBANG_JOB, jobRepository)
			.start(scrapeStatusInitializeStepConfig.zigbangScrapeStatusInitializeStep())
			.next(zigbangComplexStepConfig.zigbangComplexStep())
			.next(zigbangArticleStepConfig.zigbangArticleStep())
			.next(articleAddressStepConfig.articleAddressStep())
			.listener(jobExecutionListener)
			.build();
	}
}

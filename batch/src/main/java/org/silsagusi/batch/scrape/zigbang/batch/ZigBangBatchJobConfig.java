package org.silsagusi.batch.scrape.zigbang.batch;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.scrape.zigbang.service.ZigBangRequestService;
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
public class ZigBangBatchJobConfig {

	private static final String JOB_NAME = "zigBangItemCatalogJob";

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final ZigBangRequestService zigBangRequestService;
	private final RegionRepository regionRepository;

	@Bean
	public Job zigBangItemCatalogJob(
		Step initZigBangScrapeStatusStep,
		Step zigBangItemCatalogStep) {
		return new JobBuilder(JOB_NAME, jobRepository)
			.start(initZigBangScrapeStatusStep)
			.next(zigBangItemCatalogStep)
			.build();
	}

	@Bean
	@JobScope
	@PostConstruct
	public Step initZigBangScrapeStatusStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<Region> regions = regionRepository.findAll();
				scrapeStatusRepository.saveAll(
					regions.stream()
						.map(region -> new ScrapeStatus(
							region,
							1,
							false,
							null,
							"직방"
						))
						.toList()
				);

				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	@Bean
	@JobScope
	public Step zigBangItemCatalogStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<ScrapeStatus> regions = scrapeStatusRepository.findTop50ByCompletedFalseOrderByIdAsc();
				for (ScrapeStatus status : regions) {
					zigBangRequestService.scrapZigBang(status);
				}
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}
}

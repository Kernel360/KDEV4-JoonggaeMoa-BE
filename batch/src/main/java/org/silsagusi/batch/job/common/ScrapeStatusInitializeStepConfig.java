package org.silsagusi.batch.job.common;

import java.util.Collections;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ScrapeStatusInitializeStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final EntityManagerFactory entityManagerFactory;

	@Bean
	public Step naverLandScrapeStatusInitializeStep() {
		return new StepBuilder("naverLandScrapeStatusInitializeStep", jobRepository)
			.<ScrapeStatus, ScrapeStatus>chunk(10, transactionManager)
			.reader(scrapeStatusReader(ScrapeStatus.Platform.NAVERLAND))
			.processor(scrapeStatusInitializeProcessor())
			.writer(scrapeStatusInitializeWriter())
			.build();
	}

	@Bean
	public Step zigbangScrapeStatusInitializeStep() {
		return new StepBuilder("zigbangScrapeStatusInitializeStep", jobRepository)
			.<ScrapeStatus, ScrapeStatus>chunk(10, transactionManager)
			.reader(scrapeStatusReader(ScrapeStatus.Platform.ZIGBANG))
			.processor(scrapeStatusInitializeProcessor())
			.writer(scrapeStatusInitializeWriter())
			.build();
	}

	public ItemReader<ScrapeStatus> scrapeStatusReader(ScrapeStatus.Platform platform) {
		return new JpaPagingItemReaderBuilder<ScrapeStatus>()
			.name("scrapeStatusReader")
			.pageSize(10)
			.queryString("SELECT s FROM ScrapeStatus s WHERE s.platform = :platform")
			.parameterValues(Collections.singletonMap("platform", platform))
			.entityManagerFactory(entityManagerFactory)
			.build();
	}

	public ItemProcessor<ScrapeStatus, ScrapeStatus> scrapeStatusInitializeProcessor() {
		return ScrapeStatus::initialize;
	}

	public ItemWriter<ScrapeStatus> scrapeStatusInitializeWriter() {
		return scrapeStatusRepository::saveAll;
	}
}

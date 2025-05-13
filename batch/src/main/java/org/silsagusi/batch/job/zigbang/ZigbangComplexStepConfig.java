package org.silsagusi.batch.job.zigbang;

import org.silsagusi.batch.infrastructure.dataprovider.ScrapeStatusDataProvider;
import org.silsagusi.batch.zigbang.application.ZigbangScraper;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ZigbangComplexStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusDataProvider scrapeStatusDataProvider;
	private final ZigbangScraper zigbangScraper;

	@Bean
	public Step zigbangComplexStep() {
		return new StepBuilder("zigbangComplexStep", jobRepository)
			.<ScrapeStatus, ScrapeStatus>chunk(1, transactionManager)
			.reader(zigbangComplexReader())
			.processor(zigbangComplexProcessor())
			.writer(zigbangComplexWriter())
			.build();
	}

	public ItemReader<ScrapeStatus> zigbangComplexReader() {
		return () -> scrapeStatusDataProvider.getPendingScrapeStatus(Platform.ZIGBANG, ScrapeTargetType.COMPLEX);
	}

	public ItemProcessor<ScrapeStatus, ScrapeStatus> zigbangComplexProcessor() {
		return zigbangScraper::scrapComplex;
	}

	public ItemWriter<ScrapeStatus> zigbangComplexWriter() {
		return scrapeStatusDataProvider::saveAll;
	}
}

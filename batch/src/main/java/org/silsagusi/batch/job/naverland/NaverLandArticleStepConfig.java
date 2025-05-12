package org.silsagusi.batch.job.naverland;

import org.silsagusi.batch.infrastructure.dataprovider.ScrapeStatusDataProvider;
import org.silsagusi.batch.naverland.application.NaverLandScraper;
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
public class NaverLandArticleStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusDataProvider scrapeStatusDataProvider;
	private final NaverLandScraper naverLandScraper;

	@Bean
	public Step naverLandArticleStep() {
		return new StepBuilder("naverLandArticleStep", jobRepository)
			.<ScrapeStatus, ScrapeStatus>chunk(1, transactionManager)
			.reader(naverLandArticleReader())
			.processor(naverLandArticleProcessor())
			.writer(naverLandArticleWriter())
			.build();
	}

	public ItemReader<ScrapeStatus> naverLandArticleReader() {
		return () -> scrapeStatusDataProvider.getPendingScrapeStatus(Platform.NAVERLAND, ScrapeTargetType.ARTICLE);
	}

	public ItemProcessor<ScrapeStatus, ScrapeStatus> naverLandArticleProcessor() {
		return naverLandScraper::scrapArticle;
	}

	public ItemWriter<ScrapeStatus> naverLandArticleWriter() {
		return scrapeStatusDataProvider::saveAll;
	}
}

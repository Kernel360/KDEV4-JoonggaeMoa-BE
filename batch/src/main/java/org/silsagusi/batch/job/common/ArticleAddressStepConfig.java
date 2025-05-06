package org.silsagusi.batch.job.common;

import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.core.domain.article.Article;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ArticleAddressStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final EntityManagerFactory entityManagerFactory;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleRepository articleRepository;

	@Bean
	public Step articleAddressStep() {
		return new StepBuilder("articleAddressStep", jobRepository)
			.<Article, Article>chunk(10, transactionManager)
			.reader(articleReader())
			.processor(articleProcessor())
			.writer(articleWriter())
			.build();
	}

	@Bean
	public ItemReader<Article> articleReader() {
		return new JpaCursorItemReaderBuilder<Article>()
			.name("articleReader")
			.queryString("SELECT a FROM Article a WHERE a.addressFullLot IS NULL")
			.entityManagerFactory(entityManagerFactory)
			.build();
	}

	@Bean
	public ItemProcessor<Article, Article> articleProcessor() {
		return article -> {
			AddressResponse response = kakaoMapApiClient.lookupAddress(article.getLatitude(), article.getLongitude());
			article.updateAddress(response.getLotAddress(), response.getRoadAddress(), response.getCity(),
				response.getDistrict(), response.getRegion());
			return article;
		};
	}

	@Bean
	public ItemWriter<Article> articleWriter() {
		return articleRepository::saveAll;
	}
}

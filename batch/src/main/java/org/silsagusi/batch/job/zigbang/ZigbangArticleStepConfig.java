package org.silsagusi.batch.job.zigbang;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.zigbang.application.ZigbangScraper;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ZigbangArticleStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final ZigbangScraper zigbangScraper;

	@Bean
	public Step zigbangArticleStep() {
		return new StepBuilder("zigbangArticleStep", jobRepository)
			.tasklet(zigbangArticleTasklet(), transactionManager)
			.build();
	}

	public Tasklet zigbangArticleTasklet() {
		return (contribution, chunkContext) -> {
			while (true) {
				ScrapeStatus status = scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
					ScrapeStatus.Platform.ZIGBANG, ScrapeStatus.TargetType.ARTICLE, ScrapeStatus.Status.PENDING);

				if (status == null) {
					break; // 더 이상 처리할 status 없음
				}

				zigbangScraper.scrapArticle(status);
				status.completed();
				log.info("Completed ZigbangArticleScraper region: {}", status.getRegion().getId());
			}
			return RepeatStatus.FINISHED;
		};
	}
}

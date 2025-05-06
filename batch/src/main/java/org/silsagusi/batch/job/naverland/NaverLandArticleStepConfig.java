package org.silsagusi.batch.job.naverland;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.naverland.application.NaverLandScraper;
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
public class NaverLandArticleStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandScraper naverLandScraper;

	@Bean
	public Step naverLandArticleStep() {
		return new StepBuilder("naverLandArticleStep", jobRepository)
			.tasklet(naverLandArticleTasklet(), transactionManager)
			.build();
	}

	public Tasklet naverLandArticleTasklet() {
		return (contribution, chunkContext) -> {
			while (true) {
				ScrapeStatus status = scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
					ScrapeStatus.Platform.NAVERLAND, ScrapeStatus.TargetType.ARTICLE, ScrapeStatus.Status.PENDING);

				if (status == null) {
					break; // 더 이상 처리할 status 없음
				}

				naverLandScraper.scrapArticle(status);
				status.completed();
				log.info("Completed naverLandArticleScraper region: {}", status.getRegion().getId());
			}
			return RepeatStatus.FINISHED;
		};
	}
}

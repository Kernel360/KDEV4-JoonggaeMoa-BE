package org.silsagusi.batch.job.naverland;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.naverland.application.NaverLandScraper;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeStatusType;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
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
public class NaverLandComplexStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandScraper naverLandScraper;

	@Bean
	public Step naverLandComplexStep() {
		return new StepBuilder("naverLandComplexStep", jobRepository)
			.tasklet(naverLandComplexTasklet(), transactionManager)
			.build();
	}

	public Tasklet naverLandComplexTasklet() {
		return (contribution, chunkContext) -> {
			while (true) {
				ScrapeStatus status = scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
					Platform.NAVERLAND, ScrapeTargetType.COMPLEX, ScrapeStatusType.PENDING);

				if (status == null) {
					break;
				}

				status.inProgress();

				if (naverLandScraper.scrapComplex(status)) {
					status.completed();
				}
			}
			return RepeatStatus.FINISHED;
		};
	}
}

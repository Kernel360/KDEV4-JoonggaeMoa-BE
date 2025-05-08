package org.silsagusi.batch.job.zigbang;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.zigbang.application.ZigbangScraper;
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
public class ZigbangComplexStepConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final ZigbangScraper zigbangScraper;

	@Bean
	public Step zigbangComplexStep() {
		return new StepBuilder("zigbangComplexStep", jobRepository)
			.tasklet(zigbangComplexTasklet(), transactionManager)
			.build();
	}

	public Tasklet zigbangComplexTasklet() {
		return (contribution, chunkContext) -> {
			while (true) {
				ScrapeStatus status = scrapeStatusRepository.findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(
					Platform.ZIGBANG, ScrapeTargetType.COMPLEX, ScrapeStatusType.PENDING);

				if (status == null) {
					break;
				}

				if (zigbangScraper.scrapComplex(status)) {
					status.completed();
				}
			}
			return RepeatStatus.FINISHED;
		};
	}
}

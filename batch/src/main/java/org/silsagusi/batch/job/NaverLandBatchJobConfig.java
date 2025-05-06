package org.silsagusi.batch.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.naverland.application.NaverLandRequestService;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandScrapeRequest;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandScrapeResult;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NaverLandBatchJobConfig {

	private static final String JOB_NAME = "naverLandArticleJob";

	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final NaverLandRequestService naverLandRequestService;

	@Bean
	public Job naverLandArticleJob(Step naverLandArticleStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(naverLandArticleStep)
			.build();

		// try {
		// 	jobRegistry.register(new ReferenceJobFactory(job));
		// } catch (Exception e) {
		// 	throw new RuntimeException("Failed to register job", e);
		// }

		return job;
	}

	@Bean
	public Step naverLandArticleStep1() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				List<ScrapeStatus> regions = scrapeStatusRepository
					.findTop10BySourceAndCompletedFalseOrderByIdAsc("네이버 부동산");
				log.info("Found {} regions", regions.size());

				List<CompletableFuture<NaverLandScrapeResult>> futures = new ArrayList<>();
				for (ScrapeStatus status : regions) {
					NaverLandScrapeRequest request = new NaverLandScrapeRequest(
						status.getId(),
						status.getRegion().getId(),
						status.getRegion().getCortarNo(),
						status.getRegion().getCenterLat(),
						status.getRegion().getCenterLon(),
						status.getLastScrapedPage()
					);
					CompletableFuture<NaverLandScrapeResult> future = naverLandRequestService.scrapNaverLand(request);
					futures.add(future);
					future.whenComplete((result, throwable) -> {
						if (throwable != null) {
							NaverLandScrapeResult errorResult = new NaverLandScrapeResult(
								request.getScrapeStatusId(),
								request.getLastScrapedPage(),
								throwable.getMessage()
							);
							updateScrapeStatusByResult(errorResult);
						} else {
							updateScrapeStatusByResult(result);
						}
					});
				}
				CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	private void updateScrapeStatusByResult(NaverLandScrapeResult result) {
		scrapeStatusRepository.findById(result.scrapeStatusId()).ifPresent(status -> {
			if (result.errorMessage() != null) {
				// status.failed(result.errorMessage());
			} else {
				status.updatePage(result.lastPage(), LocalDateTime.now());
				status.completed();
			}
			scrapeStatusRepository.save(status);
		});
	}
}

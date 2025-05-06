package org.silsagusi.batch.job;

import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
import org.silsagusi.batch.zigbang.application.ZigBangRequestService;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeResult;
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
public class ZigBangBatchJobConfig {

	private static final String JOB_NAME = "zigBangItemCatalogJob";

	private final JobRegistry jobRegistry;
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final ScrapeStatusRepository scrapeStatusRepository;
	private final ZigBangRequestService zigBangRequestService;

	@Bean
	public Job zigBangItemCatalogJob(Step zigBangItemCatalogStep) {
		Job job = new JobBuilder(JOB_NAME, jobRepository)
			.start(zigBangItemCatalogStep)
			.build();

		// try {
		// 	jobRegistry.register(new ReferenceJobFactory(job));
		// } catch (Exception e) {
		// 	throw new RuntimeException("Failed to register job", e);
		// }

		return job;
	}

	@Bean
	public Step zigBangItemCatalogStep() {
		return new StepBuilder(JOB_NAME + "Step", jobRepository)
			.tasklet((contribution, chunkContext) -> {
				// List<ScrapeStatus> regions = scrapeStatusRepository.findTop10BySourceAndCompletedFalseOrderByIdAsc(
				// 	"직방");
				// for (ScrapeStatus status : regions) {
				// 	ZigBangScrapeRequest request = new ZigBangScrapeRequest(
				// 		status.getId(),
				// 		status.getRegion().getId(),
				// 		status.getRegion().getCortarNo(),
				// 		status.getLastScrapedPage(),
				// 		status.getRegion().getGeohash()
				// 	);
				// 	zigBangRequestService.scrapZigBang(request, this::updateScrapeStatusByResult);
				// }
				return RepeatStatus.FINISHED;
			}, transactionManager)
			.build();
	}

	private void updateScrapeStatusByResult(ZigBangScrapeResult result) {
		scrapeStatusRepository.findById(result.scrapeStatusId()).ifPresent(status -> {
			if (result.errorMessage() != null) {
				// status.failed(result.errorMessage());
			} else {
				status.completed();
			}
			scrapeStatusRepository.save(status);
		});
	}
}

package org.silsagusi.batch.job.common;

import org.jetbrains.annotations.NotNull;
import org.silsagusi.core.domain.article.enums.Platform;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScrapeJobStarter implements ApplicationListener<ApplicationReadyEvent> {

	private final JobRegistry jobRegistry;
	private final JobLauncher jobLauncher;

	private static final String NAVER_LAND_JOB = "naverLandJob";
	private static final String ZIGBANG_JOB = "zigbangJob";
	private static final String TIME_STAMP = "timeStamp";

	@Override
	public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

		new Thread(() -> {
			try {
				Thread.sleep(50000);
				JobParameters jobParameters = new JobParametersBuilder()
					.addString("platform", Platform.NAVERLAND.name())
					.addLong(TIME_STAMP, System.currentTimeMillis())
					.toJobParameters();

				jobLauncher.run(jobRegistry.getJob(NAVER_LAND_JOB), jobParameters);
			} catch (Exception e) {
				log.error("Error running naverLandJob", e);
			}
		}).start();

		new Thread(() -> {
			try {
				Thread.sleep(10000);
				JobParameters jobParameters = new JobParametersBuilder()
					.addString("platform", Platform.ZIGBANG.name())
					.addLong(TIME_STAMP, System.currentTimeMillis())
					.toJobParameters();

				jobLauncher.run(jobRegistry.getJob(ZIGBANG_JOB), jobParameters);
			} catch (Exception e) {
				log.error("Error running zigbangJob", e);
			}
		}).start();
	}
}

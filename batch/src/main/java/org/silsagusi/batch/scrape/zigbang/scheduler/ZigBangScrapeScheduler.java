package org.silsagusi.batch.scrape.zigbang.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ZigBangScrapeScheduler {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	private static final String SCRAPE_JOB_NAME = "zigBangItemCatalogJob";
	private static final String RESET_SCRAPE_JOB_NAME = "zigBangScrapeStatusResetJob";
	private static final String TIME_STAMP = "timeStamp";

	@Scheduled(initialDelay = 1800000, fixedRate = 1800000) // 어플리케이션 실행 30분 후 30분 간격으로 실행
	public void scrapZigBang() throws
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong(TIME_STAMP, System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob(SCRAPE_JOB_NAME), jobParameters);
	}

	// 새벽 3시에 초기화
	@Scheduled(cron = "0 0 3 * * ?")
	public void resetJob() throws
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong(TIME_STAMP, System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob(RESET_SCRAPE_JOB_NAME), jobParameters);
	}
}

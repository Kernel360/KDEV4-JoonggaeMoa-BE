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

	private static final String SCRAP_JOB_NAME = "zigBangArticleJob";
	private static final String RESET_SCRAP_JOB_NAME = "scrapStatusResetJob";
	private static final String TIME_STAMP = "timeStamp";

	@Scheduled(initialDelay = 30000, fixedRate = 1800000) // 어플리케이션 실행 30초 후 30분 간격으로 실행
	public void scrapZigBang() throws
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong(TIME_STAMP, System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob(SCRAP_JOB_NAME), jobParameters);
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

		jobLauncher.run(jobRegistry.getJob(RESET_SCRAP_JOB_NAME), jobParameters);
	}
}

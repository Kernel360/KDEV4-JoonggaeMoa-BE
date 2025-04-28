package org.silsagusi.batch.scheduler;

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

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NaverLandScrapeScheduler {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	private static final String SCRAPE_JOB_NAME = "naverLandArticleJob";
	private static final String RESET_SCRAPE_JOB_NAME = "naverLandScrapeStatusResetJob";
	private static final String TIME_STAMP = "timeStamp";

	@Scheduled(initialDelay = 5000, fixedRate = 7200000) // 어플리케이션 시작 5초 이후 2시간 간격으로 실행
	public void scrapNaverLand() throws
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

	// 새벽 2시에 초기화
	@Scheduled(cron = "0 0 2 * * ?")
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

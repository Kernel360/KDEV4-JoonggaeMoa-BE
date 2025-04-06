package org.silsagusi.joonggaemoa.request.naverland.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.silsagusi.joonggaemoa.request.naverland.service.NaverLandRequestService;
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
public class NaverLandScheduler {

	private final NaverLandRequestService naverLandRequestService;

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	private static final String SCRAP_JOB_NAME = "naverArticleJob";
	private static final String RESET_SCRAP_JOB_NAME = "scrapStatusResetJob";

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 기본값: 30분마다 실행
	@Scheduled(cron = "0 0/30 * * * *")
	public void crawlNaverLand() throws
		InterruptedException,
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		// naverLandRequestService.scrap();

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String date = dateFormat.format(new Date());

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob(SCRAP_JOB_NAME), jobParameters);
	}

	// 새벽 2시에 초기화
	@Scheduled(cron = "0 0 2 * * ?")
	public void runResetJob() throws
		NoSuchJobException,
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
			.addLong("timeStamp", System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob(RESET_SCRAP_JOB_NAME), jobParameters);
	}
}

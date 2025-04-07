package org.silsagusi.joonggaemoa.global.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;
	private static final String TIME_STAMP = "timeStamp";
	private static final String TIME_ZONE = "Asia/Seoul";

	@Scheduled(cron = "0 0/30 * * * *", zone = TIME_ZONE)
	public void scheduleNotificationsEvery30Min() throws Exception {
		log.info("Sending messages & consultation notify schedule start");

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong(TIME_STAMP, System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("messageSendingJob"), jobParameters);
		jobLauncher.run(jobRegistry.getJob("todayConsultationNotifyJob"), jobParameters);
	}

	@Scheduled(cron = "0 0 * * * *", zone = TIME_ZONE)
	public void notifyContractExpiry() throws Exception {
		log.info("Checking contract expiration schedule start");

		JobParameters jobParameters = new JobParametersBuilder()
			.addLong(TIME_STAMP, System.currentTimeMillis())
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("contractExpireNotifyJob"), jobParameters);
	}
}
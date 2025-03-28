package org.silsagusi.joonggaemoa.global.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ScheduleConfig {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	private final ReservedMessageRepository reservedMessageRepository;

	// @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
	public void sendMessages() throws Exception {
		log.info("Sending messages schedule start");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(new Date());

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("date", date)
			.toJobParameters();

		jobLauncher.run(jobRegistry.getJob("smsJob1"), jobParameters);
	}
}

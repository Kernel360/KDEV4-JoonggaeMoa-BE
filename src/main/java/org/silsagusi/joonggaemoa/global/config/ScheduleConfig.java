package org.silsagusi.joonggaemoa.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
    public void sendMessages() throws Exception {
        log.info("Sending message schedule start");

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(TIME_STAMP, System.currentTimeMillis())
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("messageSendingJob"), jobParameters);

    }

    @Scheduled(cron = "0 0/30 * * * *", zone = TIME_ZONE)
    public void notifyTodayConsultations() throws Exception {
        log.info("Checking today's consultation schedule start");

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(TIME_STAMP, System.currentTimeMillis())
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("todayConsultationNotifyJob"), jobParameters);

    }

    @Scheduled(cron = "0 0 * * * *", zone = TIME_ZONE)
    public void notifyContractExpiry() throws Exception {
        log.info("Checking contract expiration schedule start");

        JobParameters jobParameters = new JobParametersBuilder()
            .addString(TIME_STAMP, String.valueOf(System.currentTimeMillis()))
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("contractExpireNotifyJob"), jobParameters);
    }

}
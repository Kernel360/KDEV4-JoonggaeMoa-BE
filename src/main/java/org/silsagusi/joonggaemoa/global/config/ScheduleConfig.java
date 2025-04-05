package org.silsagusi.joonggaemoa.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Configuration
@EnableScheduling
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

    @Scheduled(cron = "*/30 * * * * *", zone = "Asia/Seoul")
    public void notifyContractExpiry() throws Exception {
        log.info("Checking contract expiration schedule start");

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("date", date)
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("contractExpireNotifyJob"), jobParameters);
    }

    @Scheduled(cron = "*/10 * * * * *", zone = "Asia/Seoul") // 매일 오전 9시
    public void notifyTodayConsultations() throws Exception {
        log.info("Checking today's consultation schedule start");

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        JobParameters jobParameters = new JobParametersBuilder()
            .addString("date", date)
            .addLong("timestamp", System.currentTimeMillis())
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("todayConsultationNotifyJob"), jobParameters);
    }


}

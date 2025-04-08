package org.silsagusi.joonggaemoa.request.naverland.scheduler;

import lombok.RequiredArgsConstructor;
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

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NaverLandScheduler {

    private final NaverLandRequestService naverLandRequestService;

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    private static final String SCRAP_JOB_NAME = "naverArticleJob";
    private static final String RESET_SCRAP_JOB_NAME = "scrapStatusResetJob";
    private static final String TIME_STAMP = "timeStamp";

    // 기본값: 30분마다 실행
    @Scheduled(initialDelay = 5000, fixedRate = 1800000) // 1800000ms = 30분
    public void scrapNaverLand() throws
        InterruptedException,
        NoSuchJobException,
        JobInstanceAlreadyCompleteException,
        JobExecutionAlreadyRunningException,
        JobParametersInvalidException,
        JobRestartException {
        // naverLandRequestService.scrap();

        JobParameters jobParameters = new JobParametersBuilder()
            .addLong(TIME_STAMP, System.currentTimeMillis())
            .toJobParameters();

        jobLauncher.run(jobRegistry.getJob(SCRAP_JOB_NAME), jobParameters);
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

        jobLauncher.run(jobRegistry.getJob(RESET_SCRAP_JOB_NAME), jobParameters);
    }
}

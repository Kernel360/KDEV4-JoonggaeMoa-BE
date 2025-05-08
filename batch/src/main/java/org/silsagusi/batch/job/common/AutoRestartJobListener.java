package org.silsagusi.batch.job.common;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutoRestartJobListener implements JobExecutionListener {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	private static final String NAVER_LAND_JOB = "naverLandJob";
	private static final String ZIGBANG_JOB = "zigbangJob";
	private static final String TIME_STAMP = "timeStamp";

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			String jobName = jobExecution.getJobInstance().getJobName();

			try {
				Thread.sleep(15000);
				JobParametersBuilder jobParametersBuilder = new JobParametersBuilder().
					addLong(TIME_STAMP, System.currentTimeMillis());

				if (NAVER_LAND_JOB.equals(jobName)) {
					jobParametersBuilder.addString("platform", "NAVERLAND");
					jobLauncher.run(jobRegistry.getJob(NAVER_LAND_JOB), jobParametersBuilder.toJobParameters());
				} else if (ZIGBANG_JOB.equals(jobName)) {
					jobParametersBuilder.addString("platform", "ZIGBANG");
					jobLauncher.run(jobRegistry.getJob(ZIGBANG_JOB), jobParametersBuilder.toJobParameters());
				}

			} catch (Exception e) {
				log.error("Failed to auto restart job", e);
			}
		}
	}
}

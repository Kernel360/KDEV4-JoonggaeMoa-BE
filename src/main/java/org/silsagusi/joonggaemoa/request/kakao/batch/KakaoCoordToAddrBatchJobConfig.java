package org.silsagusi.joonggaemoa.request.kakao.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KakaoCoordToAddrBatchJobConfig {

	public static final String JOB_NAME = "kakaoCoordToAddrJob";
	public static final int CHUNK_SIZE = 20;

	private final JobRegistry jobRegistry;

	@Bean
	public Job kakaoCoordToAddrJob(Step kakaoCoordToAddrStep) throws NoSuchJobException {
		return jobRegistry.getJob(JOB_NAME);
	}
}

package org.silsagusi.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

	@Bean
	public JobRepository jobRepository(
		@Qualifier("metaDbDataSource") DataSource metaDbDataSource,
		@Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager
	) throws Exception {
		JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
		factoryBean.setDataSource(metaDbDataSource);
		factoryBean.setTransactionManager(metaTransactionManager);
		factoryBean.setDatabaseType("mysql");
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	@Bean
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}
}

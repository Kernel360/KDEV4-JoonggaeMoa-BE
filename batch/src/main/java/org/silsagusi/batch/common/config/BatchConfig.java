package org.silsagusi.batch.common.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

	private final DataSource metaDbDataSource;
	private final PlatformTransactionManager metaTransactionManager;

	public BatchConfig(@Qualifier("metaDbDataSource") DataSource metaDbDataSource,
		@Qualifier("metaTransactionManager") PlatformTransactionManager metaTransactionManager) {
		this.metaDbDataSource = metaDbDataSource;
		this.metaTransactionManager = metaTransactionManager;
	}

	@Override
	public JobRepository jobRepository() {
		JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
		factoryBean.setDataSource(metaDbDataSource);
		factoryBean.setTransactionManager(metaTransactionManager);
		factoryBean.setDatabaseType("mysql");
		try {
			factoryBean.afterPropertiesSet();
			return factoryBean.getObject();
		} catch (Exception e) {
			log.error("Error while setting up JobRepository", e);
			return null;
		}
	}

	@Override
	protected PlatformTransactionManager getTransactionManager() {
		return metaTransactionManager;
	}

	// @Bean
	// public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
	// 	TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
	// 	jobLauncher.setJobRepository(jobRepository);
	// 	jobLauncher.afterPropertiesSet();
	// 	return jobLauncher;
	// }
	//
	// @Bean
	// public JobRegistry jobRegistry() {
	// 	return new MapJobRegistry();
	// }
	//
	// @Bean
	// public JobRegistrySmartInitializingSingleton jobRegistrySmartInitializingSingleton(JobRegistry jobRegistry) {
	// 	return new JobRegistrySmartInitializingSingleton(jobRegistry);
	// }

	// @Bean
	// public static StepScope stepScope() {
	// 	return new StepScope();
	// }
}

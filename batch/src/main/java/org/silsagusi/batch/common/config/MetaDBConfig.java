package org.silsagusi.batch.common.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MetaDBConfig {

	@Bean(name = "metaDbDataSource")
	@ConfigurationProperties(prefix = "spring.datasource-meta")
	public DataSource metaDbDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "metaTransactionManager")
	public PlatformTransactionManager metaTransactionManager() {
		return new DataSourceTransactionManager(metaDbDataSource());
	}
}

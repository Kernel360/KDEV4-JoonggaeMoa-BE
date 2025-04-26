package org.silsagusi.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

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

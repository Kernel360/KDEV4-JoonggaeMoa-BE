package org.silsagusi.joonggaemoa.global.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MetaDBConfig {

	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource-meta")
	public DataSource metaDbDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public PlatformTransactionManager metaTransactionManager() {
		return new DataSourceTransactionManager(metaDbDataSource());
	}
}

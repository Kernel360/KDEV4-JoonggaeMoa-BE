package org.silsagusi.api.common.config;

import org.silsagusi.core.config.DataDBConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
	basePackages = {"org.silsagusi.api"},
	transactionManagerRef = "dataTransactionManager"
)
@Import(DataDBConfig.class)
public class JpaConfig {
}

package org.silsagusi.api.common.config;

import org.silsagusi.core.config.DataDBConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaRepositories(
	basePackages = {"org.silsagusi.api"},
	entityManagerFactoryRef = "dataEntityManager",
	transactionManagerRef = "dataTransactionManager"
)
@Import(DataDBConfig.class)
@RequiredArgsConstructor
public class JpaConfig {

	@PersistenceContext
	private final EntityManager entityManager;

	@Bean
	public JPAQueryFactory jpaQueryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}

package org.silsagusi.joonggaemoa.global.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
			.expireAfterWrite(1, TimeUnit.HOURS)
			.maximumSize(10000);

		CaffeineCacheManager cacheManager = new CaffeineCacheManager("addressCache");
		cacheManager.setCaffeine(caffeine);
		return cacheManager;
	}
}

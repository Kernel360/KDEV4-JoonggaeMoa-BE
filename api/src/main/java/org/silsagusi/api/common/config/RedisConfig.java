package org.silsagusi.api.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	@Value("${spring.data.redis.password}")
	private String password;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setPassword(password);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}
}

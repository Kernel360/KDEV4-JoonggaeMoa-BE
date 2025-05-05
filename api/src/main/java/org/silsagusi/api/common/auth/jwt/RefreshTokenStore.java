package org.silsagusi.api.common.auth.jwt;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenStore {

	private static final String REFRESH_TOKEN_PREFIX = "RTK:";
	private final RedisTemplate<String, String> redisTemplate;

	public void saveRefreshToken(String username, String refreshToken, Long expirationTimeInSeconds) {
		redisTemplate.opsForValue()
			.set(REFRESH_TOKEN_PREFIX + username, refreshToken, expirationTimeInSeconds, TimeUnit.SECONDS);
	}

	public void deleteRefreshToken(String username) {
		redisTemplate.delete(REFRESH_TOKEN_PREFIX + username);
	}

	public String getRefreshToken(String username) {
		return redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + username);
	}
}

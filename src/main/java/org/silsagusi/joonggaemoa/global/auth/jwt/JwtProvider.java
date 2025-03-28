package org.silsagusi.joonggaemoa.global.auth.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

	private static final long ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60 * 1000L;
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey key;

	@PostConstruct
	public void init() {
		key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	public Claims getClaims(final String token) {
		if (Boolean.FALSE.equals(validateToken(token))) {
			return null;
		}

		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public String getTokenId(final String token) {
		return getClaims(token).getId();
	}

	public Long getExpirationTime(final String token) {
		return getClaims(token).getExpiration().getTime();
	}

	public String getSubject(final String token) {
		return getClaims(token).getSubject();
	}

	public Boolean isTokenExpired(final String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getExpiration();
			return false;
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token: {}", e.getMessage());
		}
		return true;
	}

	public String generateAccessToken(final Long id, String username, String role) {
		return Jwts.builder()
			.setId(id + "")
			.claim("username", username)
			.claim("role", role)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
			.signWith(key)
			.compact();
	}

	public String generateRefreshToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.signWith(key)
			.compact();
	}

	public Boolean validateToken(final String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (SecurityException e) {
			log.warn("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Invalid JWT token: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("Unsupported JWT token: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("JWT claims string is empty: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.warn("Expired JWT token: {}", e.getMessage());
		}
		return false;
	}
}

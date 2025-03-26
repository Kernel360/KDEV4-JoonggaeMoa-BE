package org.silsagusi.joonggaemoa.global.auth.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import org.silsagusi.joonggaemoa.domain.agent.controller.dto.LoginRequest;
import org.silsagusi.joonggaemoa.global.auth.jwt.JwtProvider;
import org.silsagusi.joonggaemoa.global.auth.jwt.RefreshTokenStore;
import org.silsagusi.joonggaemoa.global.auth.userDetails.CustomUserDetails;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final RefreshTokenStore refreshTokenStore;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider,
		ObjectMapper objectMapper, RefreshTokenStore refreshTokenStore) {
		this.authenticationManager = authenticationManager;
		this.jwtProvider = jwtProvider;
		this.objectMapper = objectMapper;
		this.refreshTokenStore = refreshTokenStore;
		setFilterProcessesUrl("/api/agent/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		LoginRequest requestDto = null;
		try {
			requestDto = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			requestDto.getUsername(), requestDto.getPassword());

		try {
			return authenticationManager.authenticate(authToken);
		} catch (AuthenticationException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		CustomUserDetails userDetails = (CustomUserDetails)authResult.getPrincipal();

		Long id = userDetails.getId();
		String username = userDetails.getUsername();
		String role = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		String accessToken = jwtProvider.generateAccessToken(id, username, role);
		String refreshToken = jwtProvider.generateRefreshToken(username);
		Long expirationTime = jwtProvider.getExpirationTime(refreshToken);

		refreshTokenStore.saveRefreshToken(username, refreshToken, expirationTime);
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(86400)
			.build();

		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Authorization", "Bearer " + accessToken);
		response.addHeader("Set-Cookie", cookie.toString());
	}
}

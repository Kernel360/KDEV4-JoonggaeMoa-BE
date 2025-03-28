package org.silsagusi.joonggaemoa.global.auth.filter;

import java.io.IOException;
import java.util.Collections;

import org.silsagusi.joonggaemoa.global.auth.jwt.JwtProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");

		String username = null;
		String role = null;

		if (token != null && !token.isEmpty()) {
			token = token.substring(7);
			Claims claims = jwtProvider.getClaims(token);
			username = claims.get("username", String.class);
			role = claims.get("role", String.class);
		}

		if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
			Authentication authentication = getUserAuth(username, role);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getUserAuth(String username, String role) {
		return new UsernamePasswordAuthenticationToken(
			username, "",
			Collections.singleton(new SimpleGrantedAuthority(role))
		);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return path.equals("/api/agent/login") || path.equals("/api/agent/signup") || path.equals("/api/refresh-token");
	}
}

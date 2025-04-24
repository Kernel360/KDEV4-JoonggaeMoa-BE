package org.silsagusi.api.auth;

import java.util.Arrays;
import java.util.List;

import org.silsagusi.api.auth.filter.JwtAuthenticationFilter;
import org.silsagusi.api.auth.filter.JwtAuthorizationFilter;
import org.silsagusi.api.auth.jwt.JwtProvider;
import org.silsagusi.api.auth.jwt.RefreshTokenStore;
import org.silsagusi.api.auth.userDetails.CustomUserDetailsService;
import org.silsagusi.api.exception.handler.CustomAccessDeniedHandler;
import org.silsagusi.api.exception.handler.CustomAuthenticationEntryPointHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = {
		"/api/agents/login",
		"/api/agents/signup",
		"/api/refresh-token",
		"/api/customers/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/api/notification/subscribe",
		"/api/inquiries/**"
	};
	private final CustomAuthenticationEntryPointHandler customAuthenticationEntryPointHandler;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;
	private final JwtProvider jwtProvider;
	private final ObjectMapper objectMapper;
	private final CustomUserDetailsService customUserDetailsService;
	private final RefreshTokenStore refreshTokenStore;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.cors(configurer -> configurer.configurationSource(corsConfigurationSource()))

			.addFilterAt(
				new JwtAuthenticationFilter(authenticationManager(customUserDetailsService), jwtProvider, objectMapper,
					refreshTokenStore), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)

			.authorizeHttpRequests(auth -> auth
					.requestMatchers(AUTH_WHITELIST).permitAll()
					.anyRequest().authenticated()
				//.anyRequest().permitAll()
			)

			.exceptionHandling(configurer -> configurer
				.authenticationEntryPoint(customAuthenticationEntryPointHandler)
				.accessDeniedHandler(customAccessDeniedHandler)
			);

		return httpSecurity.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("Authorization");
		corsConfiguration.addExposedHeader("Agentid");
		corsConfiguration.setAllowCredentials(true);

		corsConfiguration.setAllowedOrigins(
			List.of("http://localhost:5173", "https://www.joonggaemoa.com")
		);

		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
}

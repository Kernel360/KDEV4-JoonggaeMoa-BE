package org.silsagusi.api.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityProperties {

	public static final String[] PATHS = {
		"/api/agents/login",
		"/api/agents/signup",
		"/api/refresh-token",
		"/api/customers/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/api/notification/subscribe",
		"/api/inquiries/**"
	};
}

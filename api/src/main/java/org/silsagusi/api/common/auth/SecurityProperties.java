package org.silsagusi.api.common.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityProperties {

	public static final String[] PATHS = {
		"/api/agents/login",
		"/api/agents/signup",
		"/api/refresh-token",
		"/api/customers/surveys/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/api/notifications/subscribe",
		"/api/inquiries/**",
		"/health"
	};
}

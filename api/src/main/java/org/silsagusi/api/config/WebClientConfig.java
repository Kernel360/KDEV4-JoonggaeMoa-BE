package org.silsagusi.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient geminiWebClient() {
		return WebClient.builder()
			.baseUrl("https://generativelanguage.googleapis.com")
			.build();
	}
}

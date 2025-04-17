package org.silsagusi.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient naverRegionWebClient() {
		return WebClient.builder()
			.baseUrl("https://new.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.build();
	}

	@Bean
	public WebClient naverWebClient() {
		return WebClient.builder()
			.baseUrl("https://m.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.build();
	}

	// kakao.api.key: application.yml에 설정된 Kakao REST API 키 (KakaoAK {API_KEY})
	@Bean
	public WebClient kakaoWebClient(@Value("${kakao.api.key}") String kakaoApiKey) {
		return WebClient.builder()
			.baseUrl("https://dapi.kakao.com")
			.defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
			.build();
	}

	@Bean
	public WebClient zigBangWebClient() {
		return WebClient.builder()
			.baseUrl("https://apis.zigbang.com/")
			.build();
	}
}

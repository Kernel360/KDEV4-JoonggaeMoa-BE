package org.silsagusi.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient naverRegionWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // Connection timeout: 30 seconds
			.responseTimeout(Duration.ofSeconds(30)) // Response timeout: 30 seconds
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)) // Read timeout: 30 seconds
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS))); // Write timeout: 30 seconds

		return WebClient.builder()
			.baseUrl("https://new.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	@Bean
	public WebClient naverWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // Connection timeout: 30 seconds
			.responseTimeout(Duration.ofSeconds(30)) // Response timeout: 30 seconds
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)) // Read timeout: 30 seconds
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS))); // Write timeout: 30 seconds

		return WebClient.builder()
			.baseUrl("https://m.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	// kakao.api.key: application.yml에 설정된 Kakao REST API 키 (KakaoAK {API_KEY})
	@Bean
	public WebClient kakaoWebClient(@Value("${kakao.api.key}") String kakaoApiKey) {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // Connection timeout: 30 seconds
			.responseTimeout(Duration.ofSeconds(30)) // Response timeout: 30 seconds
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)) // Read timeout: 30 seconds
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS))); // Write timeout: 30 seconds

		return WebClient.builder()
			.baseUrl("https://dapi.kakao.com")
			.defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}

	@Bean
	public WebClient zigBangWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000) // Connection timeout: 30 seconds
			.responseTimeout(Duration.ofSeconds(30)) // Response timeout: 30 seconds
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)) // Read timeout: 30 seconds
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS))); // Write timeout: 30 seconds

		return WebClient.builder()
			.baseUrl("https://apis.zigbang.com/")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.build();
	}
}

package org.silsagusi.batch.common.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	private ExchangeStrategies getExchangeStrategies() {
		return ExchangeStrategies.builder()
			.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
			.build();
	}

	@Bean
	public WebClient naverRegionWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(30))
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

		return WebClient.builder()
			.baseUrl("https://new.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.exchangeStrategies(getExchangeStrategies())
			.build();
	}

	@Bean
	public WebClient naverWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(30))
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

		return WebClient.builder()
			.baseUrl("https://m.land.naver.com")
			.defaultHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.exchangeStrategies(getExchangeStrategies())
			.build();
	}

	@Bean
	public WebClient kakaoWebClient(@Value("${kakao.api.key}") String kakaoApiKey) {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(30))
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

		return WebClient.builder()
			.baseUrl("https://dapi.kakao.com")
			.defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.exchangeStrategies(getExchangeStrategies())
			.build();
	}

	@Bean
	public WebClient zigBangWebClient() {
		HttpClient httpClient = HttpClient.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
			.responseTimeout(Duration.ofSeconds(30))
			.doOnConnected(conn -> conn
				.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS))
				.addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));

		return WebClient.builder()
			.baseUrl("https://apis.zigbang.com/")
			.clientConnector(new ReactorClientHttpConnector(httpClient))
			.exchangeStrategies(getExchangeStrategies())
			.build();
	}
}
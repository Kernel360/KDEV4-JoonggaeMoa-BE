package org.silsagusi.batch.scrape.naverland.client;

import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapCoord2AddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoMapApiClient {

	private final WebClient kakaoWebClient;

	public KakaoMapApiClient(WebClient kakaoWebClient) {
		this.kakaoWebClient = kakaoWebClient;
	}

	public KakaoMapCoord2AddressResponse getAddr(double longitude, double latitude) {
		return kakaoWebClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/v2/local/geo/coord2address.json")
				.queryParam("x", longitude)
				.queryParam("y", latitude)
				.queryParam("input_coord", "WGS84") // 입력 좌표 체계
				.build())
			.retrieve()
			.bodyToMono(KakaoMapCoord2AddressResponse.class)
			.block(); // 블로킹 방식으로 결과 반환
	}
}
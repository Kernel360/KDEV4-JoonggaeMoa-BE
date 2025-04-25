package org.silsagusi.batch.naverland.client;

import org.silsagusi.batch.naverland.service.dto.Coord2AddressResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApiClient {

	private final WebClient kakaoWebClient;

	public KakaoApiClient(WebClient kakaoWebClient) {
		this.kakaoWebClient = kakaoWebClient;
	}

	public Coord2AddressResponse getAddr(double longitude, double latitude) {
		return kakaoWebClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/v2/local/geo/coord2address.json")
				.queryParam("x", longitude)
				.queryParam("y", latitude)
				.queryParam("input_coord", "WGS84") // 입력 좌표 체계
				.build())
			.retrieve()
			.bodyToMono(Coord2AddressResponse.class)
			.block(); // 블로킹 방식으로 결과 반환
	}
}
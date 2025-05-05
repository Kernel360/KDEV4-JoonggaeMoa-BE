package org.silsagusi.batch.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoMapApiClient {

	private final WebClient kakaoWebClient;

	@Cacheable(value = "addressCache", key = "#latitude + ':' + #longitude")
	public AddressResponse lookupAddress(double longitude, double latitude) {
		KakaoMapCoord2AddressResponse response = kakaoWebClient.get()
			.uri(uriBuilder -> uriBuilder
				.path("/v2/local/geo/coord2address.json")
				.queryParam("x", longitude)
				.queryParam("y", latitude)
				.queryParam("input_coord", "WGS84") // 입력 좌표 체계
				.build())
			.retrieve()
			.bodyToMono(KakaoMapCoord2AddressResponse.class)
			.block();

		return KakaoMapCoord2AddressResponse.toResponse(response);
	}
}
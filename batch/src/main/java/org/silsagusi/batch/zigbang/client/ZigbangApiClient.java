package org.silsagusi.batch.zigbang.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class ZigbangApiClient {

	private final WebClient zigBangWebClient;

	/*
	최소 평수 및 최대 평수
	띄어쓰기 없음
	*/
	private static final String minPynArea = "10평이하";

	private static final String maxPynArea = "60평대이상";

	private<T> T fetchApt(String path, String geohash, Class<T> responseType) {
		return zigBangWebClient.get()
			.uri(uriBuilder -> uriBuilder.path(path)
				.queryParam("minPynArea", minPynArea)
				.queryParam("maxPynArea", maxPynArea)
				.queryParam("geohash", geohash)
				.build()
			)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(responseType)
			.block();
	}

//	public <T> T fetchVilla(String path, String geohash, Class<T> responseType) {
//		return zigBangWebClient.get()
//			.uri(uriBuilder -> uriBuilder.path(path)
//				.queryParam("geohash", geohash)
//				.build())
//			.accept(MediaType.APPLICATION_JSON)
//			.retrieve()
//			.bodyToMono(responseType)
//			.block();
//	}

}

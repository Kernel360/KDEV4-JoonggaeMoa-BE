package org.silsagusi.joonggaemoa.request.naverland.service;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientRegionResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final WebClient naverRegionWebClient;

	public ClientRegionResponse fetchRegionList(String cortarNo) {
		ClientRegionResponse response = naverRegionWebClient.get()
			.uri(uriBuilder -> uriBuilder.path("/api/regions/list")
				.queryParam("cortarNo", cortarNo)
				.build())
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(ClientRegionResponse.class)
			.block();

		return response;
	}
}

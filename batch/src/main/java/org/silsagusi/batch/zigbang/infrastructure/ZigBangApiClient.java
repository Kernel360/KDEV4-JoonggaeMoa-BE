package org.silsagusi.batch.zigbang.infrastructure;

import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ZigBangApiClient {

	private final WebClient zigBangWebClient;

	public ZigBangItemCatalogResponse fetchItemCatalog(String localCode, Integer offset) {
		return zigBangWebClient.get()
			.uri(uriBuilder -> uriBuilder.path(
						String.format("/apt/locals/%s/item-catalogs", localCode)
					)
					//					.queryParam("tranTypeIn[0]", "trade") // 매매
					//					.queryParam("tranTypeIn[1]", "charter") // 전세
					//					.queryParam("tranTypeIn[2]", "rental") // 월세
					//					.queryParam("includeOfferItem", "true") // 신규 분양 포함
					.queryParam("offset", offset)
					// .queryParam("limit", "2100000000")
					.build()
			)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(ZigBangItemCatalogResponse.class)
			.block();
	}

	public ZigBangDanjiResponse fetchDanji(String geohash) {
		return zigBangWebClient.get()
			.uri(uriBuilder -> uriBuilder.path("/apt/locals/prices/on-danjis")
				//				.queryParam("minPynArea", "10평이하") // 최소 평수
				//				.queryParam("maxPynArea", "60평대이상") // 최대 평수
				.queryParam("geohash", geohash)
				.build()
			)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.bodyToMono(ZigBangDanjiResponse.class)
			.block();
	}
}

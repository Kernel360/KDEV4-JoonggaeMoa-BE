package org.silsagusi.batch.zigbang.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.zigbang.infrastructure.ZigBangApiClient;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeRequest;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final RegionRepository regionRepository;

	@Async("scrapeExecutor")
	public void scrapZigBang(ZigBangScrapeRequest request, java.util.function.Consumer<org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeResult> callback) {
		Region region = regionRepository.findById(request.getRegionId())
			.orElseThrow(() -> new IllegalArgumentException("Region not found with id: " + request.getRegionId()));
		String geohash = region.getGeohash().substring(0, 5);
		String localCode = region.getCortarNo().substring(0, 8);

		List<Complex> allComplexes = new ArrayList<>();
		Set<Integer> seenDanjiIds = new HashSet<>();
		Map<Integer, Complex> idToComplex = new HashMap<>();

		// 단지 스크래핑
		try {
			ZigBangDanjiResponse danjiResp = zigbangApiClient.fetchDanji(geohash);
			for (ZigBangDanjiResponse.ZigBangDanji dto : danjiResp.getFiltered()) {
				if (seenDanjiIds.add(dto.getId())) {
					Complex danji = complexDataProvider.createZigBangDanji(dto, region);
					allComplexes.add(danji);
					idToComplex.put(dto.getId(), danji);
				}
			}
			complexDataProvider.saveComplexes(allComplexes);

			// 매물 스크래핑
			ZigBangItemCatalogResponse itemResp = zigbangApiClient.fetchItemCatalog(localCode);
			AddressResponse kakaoResp = kakaoMapApiClient.lookupAddress(
				danjiResp.getFiltered().get(0).getLng(),
				danjiResp.getFiltered().get(0).getLat()
			); // 주소 불러오기

			for (ZigBangItemCatalogResponse.ZigBangItemCatalog item : itemResp.getList()) {
				Complex complex = idToComplex.get(item.getAreaDanjiId());
				String dongName = kakaoResp.getRegion(); // 법정동코드 불러오기
				Region dongRegion = regionRepository.findByCortarName(dongName);
				String cortarNo = (dongRegion != null) ? dongRegion.getCortarNo() : null;
				if (cortarNo == null) {
					log.debug("{} 카카오 API 응답없음", dongName);
				}

				Article article = articleDataProvider.createZigBangItemCatalog(
					item, danjiResp, kakaoResp, region, complex, cortarNo);
				articleDataProvider.saveArticles(List.of(article));
			}

			long delay = (long)(Math.random() * 10000);
			java.util.concurrent.Executors.newSingleThreadScheduledExecutor().schedule(() ->
				callback.accept(
					new org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeResult(
						request.getScrapeStatusId(), null)
				), delay, java.util.concurrent.TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			callback.accept(
				new org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeResult(
					request.getScrapeStatusId(), e.getMessage())
			);
		}
	}
}

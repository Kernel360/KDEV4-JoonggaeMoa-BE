package org.silsagusi.batch.zigbang.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
import org.silsagusi.batch.zigbang.infrastructure.ZigBangApiClient;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;

	@Async("scrapeExecutor")
	public void scrapZigBang(ScrapeStatus scrapeStatus) throws InterruptedException {
		List<Article> articles = new ArrayList<>();
		Region region = scrapeStatus.getRegion();
		String localCode = region.getCortarNo().substring(0, 8);
		String geohash = region.getGeohash().substring(0, 5);

		List<Complex> allComplexes = new ArrayList<>();
		Set<Integer> seenDanjiIds = new HashSet<>();
		Map<Integer, Complex> idToComplex = new HashMap<>();

		// 단지 스크래핑
		ZigBangDanjiResponse danjiResp = zigbangApiClient.fetchDanji(geohash);
		for (ZigBangDanjiResponse.ZigBangDanji dto : danjiResp.getFiltered()) {
			if (seenDanjiIds.add(dto.getId())) {
				Complex danji = complexDataProvider.createZigBangDanji(dto, region);
				allComplexes.add(danji);
				idToComplex.put(dto.getId(), danji);
			}
		}

		// 매물 스크래핑
		ZigBangItemCatalogResponse itemResp = zigbangApiClient.fetchItemCatalog(localCode);
		for (ZigBangItemCatalogResponse.ZigBangItemCatalog item : itemResp.getList()) {
			Complex complex = idToComplex.get(item.getAreaDanjiId());
			AddressResponse kakaoResp = null;
			int retryCount = 0;
			// 최대 3회 주소 조회 재시도
			while (retryCount < 3) {
				kakaoResp = kakaoMapApiClient.lookupAddress(
					danjiResp.getFiltered().get(0).getLat(), danjiResp.getFiltered().get(0).getLng()
				);
				if (kakaoResp != null) {
					break;
				}
				retryCount++;
				Thread.sleep(500);
			}
			if (kakaoResp == null) {
				continue;
			}
			Article article = articleDataProvider.createZigBangItemCatalog(item, danjiResp, kakaoResp, region, complex);
			articles.add(article);
			Thread.sleep((long)(Math.random() * 1000)); // 0~1초 랜덤 대기
		}

		complexDataProvider.saveComplexes(allComplexes);
		articleDataProvider.saveArticles(articles);
		scrapeStatus.completed();

		Thread.sleep((long)(Math.random() * 1000));
	}
}

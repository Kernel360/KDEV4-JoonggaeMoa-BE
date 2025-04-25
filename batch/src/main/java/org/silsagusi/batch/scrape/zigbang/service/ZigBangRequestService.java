package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.scrape.naverland.service.KakaoMapAddressLookupService;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.zigbang.client.ZigBangApiClient;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangDanjiResponse;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final KakaoMapAddressLookupService addressLookupService;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;

	@Async("scrapeExecutor")
	public void scrapZigBang(ScrapeStatus scrapeStatus) throws InterruptedException {
		List<Article> articles = new ArrayList<>();
		Region region = scrapeStatus.getRegion();
		String localCode = region.getCortarNo().substring(0, 8);
		String geohash = region.getGeohash().substring(0,5);

		List<Complex> allComplexes = new ArrayList<>();
		Set<Integer> seenDanjiIds = new HashSet<>();
		Map<Integer, Complex> idToComplex = new HashMap<>();

		// 단지 스크래핑
		ZigBangDanjiResponse danjiResp = zigbangApiClient.fetchDanji(geohash);
		for (ZigBangDanjiResponse.ZigBangDanji dto : danjiResp.getFiltered()) {
			if (seenDanjiIds.add(dto.getId())) {
				Complex danji = ComplexDataProvider.createZigBangDanji(dto, region);
				allComplexes.add(danji);
				idToComplex.put(dto.getId(), danji);
			}
		}

		// 매물 스크래핑
		ZigBangItemCatalogResponse itemResp = zigbangApiClient.fetchItemCatalog(localCode);
		for (ZigBangItemCatalogResponse.ZigBangItemCatalog item : itemResp.getList()) {
			Complex complex = idToComplex.get(item.getAreaDanjiId());
			KakaoMapAddressResponse kakaoResp = addressLookupService.lookupAddress(
					danjiResp.getFiltered().get(0).getLat(), danjiResp.getFiltered().get(0).getLng()
				);

			Article article = ArticleDataProvider.createZigBangItemCatalog(item, danjiResp, kakaoResp, region, complex);
			articles.add(article);
		}

		complexDataProvider.saveComplexes(allComplexes);
		articleDataProvider.saveArticles(articles);
		scrapeStatus.updateCompleted(true);

		Thread.sleep((long) (Math.random() * 1000));
	}
}

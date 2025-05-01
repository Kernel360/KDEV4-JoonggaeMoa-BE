package org.silsagusi.batch.zigbang.application;

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
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangScrapeResult;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final RegionRepository regionRepository;

	private static final ScheduledExecutorService scheduler =
		Executors.newSingleThreadScheduledExecutor();

	@Async("scrapeExecutor")
	public CompletableFuture<ZigBangScrapeResult> scrapZigBang(ZigBangScrapeRequest request) {
		CompletableFuture<ZigBangScrapeResult> future = new CompletableFuture<>();
		Region region = regionRepository.findById(request.getRegionId())
			.orElseThrow(() -> new IllegalArgumentException("Region not found with id: " + request.getRegionId()));
		String geohash = region.getGeohash().substring(0, 5);
		String localCode = region.getCortarNo().substring(0, 8);

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
		complexDataProvider.saveComplexes(allComplexes);

		// 매물 스크래핑
		int page = request.getLastScrapedPage();
		int offset = 0;
		while (true) {
			ZigBangItemCatalogResponse itemResp = zigbangApiClient.fetchItemCatalog(localCode, offset);
			AddressResponse kakaoResp = kakaoMapApiClient.lookupAddress(
				danjiResp.getFiltered().get(0).getLng(),
				danjiResp.getFiltered().get(0).getLat()
			);

			for (ZigBangItemCatalogResponse.ZigBangItemCatalog item : itemResp.getList()) {
				Complex complex = idToComplex.get(item.getAreaDanjiId());
				String dongName = kakaoResp.getRegion();
				Region dongRegion = regionRepository.findByCortarName(dongName);
				String cortarNo = (dongRegion != null) ? dongRegion.getCortarNo() : null;
				if (cortarNo == null) {
					log.debug("{} 카카오 API 응답없음", dongName);
				}
				page++;
				offset += 20;
				Article article = articleDataProvider.createZigBangItemCatalog(
					item, danjiResp, kakaoResp, region, complex, cortarNo);
				articleDataProvider.saveArticles(List.of(article));

			}
			if (itemResp.getList().isEmpty()) {
				break;
			}
			long delay = (long) (3000 + Math.random() * 4000);
			int finalPage = page;
			scheduler.schedule(() ->
					future.complete(new ZigBangScrapeResult(request.getScrapeStatusId(), finalPage, null)),
				delay, TimeUnit.MILLISECONDS
			);
		}
		return future;
	}
}

package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.ArticleDataProvider;
import org.silsagusi.batch.scrape.zigbang.client.ZigBangApiClient;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangItemCatalogRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapZigBangItemCatalogs(ScrapeStatus scrapeStatus) throws InterruptedException {
		List<Article> articles = new ArrayList<>();

		try {
			log.info("직방 스크랩 시작: 지역 코드 {}", scrapeStatus.getRegion().getCortarNo());
			Region region = scrapeStatus.getRegion();

			if (!"sec".equals(region.getCortarType())) {
				log.info("cortarType이 sec가 아니므로 스크랩을 건너뜁니다: 지역 코드 {}", region.getCortarNo());
				return;
			}

			String geohash = region.getGeohash().substring(0, 5);

			ZigBangItemCatalogResponse catalog = zigbangApiClient.fetchItemCatalog(geohash);
			log.info("직방 데이터 조회 성공: 지역 코드 {}, 매물 수 {}", 
				scrapeStatus.getRegion().getCortarNo(), 
				catalog.getList() != null ? catalog.getList().size() : 0);

			articles.addAll(mapZigBangToArticles(catalog.getList(), scrapeStatus.getRegion()));
			scrapeStatus.updateCompleted(true);
			log.info("직방 스크랩 완료: 지역 코드 {}, 처리된 매물 수 {}", 
				scrapeStatus.getRegion().getCortarNo(), articles.size());
		} catch (Exception e) {
			log.error("직방 스크랩 실패: 지역 코드 {}, 에러 메시지: {}", 
				scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			scrapeStatus.updateFailed(true, e.getMessage());
		} finally {
			try {
				articleDataProvider.saveArticles(articles);
				log.info("직방 매물 저장 완료: 지역 코드 {}, 저장된 매물 수 {}", 
					scrapeStatus.getRegion().getCortarNo(), articles.size());
			} catch (Exception e) {
				log.error("직방 매물 저장 실패: 지역 코드 {}, 에러 메시지: {}", 
					scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			}
			Thread.sleep(100);
		}
	}

	private List<Article> mapZigBangToArticles(
		List<ZigBangItemCatalogResponse.ZigBangItemCatalog> items,
		Region region
	) {
		return items.stream()
			.map(item -> ArticleDataProvider.createZigBangArticle(item, region))
			.toList();
	}
}

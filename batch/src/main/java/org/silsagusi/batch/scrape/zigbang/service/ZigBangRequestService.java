package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.infrastructure.repository.ScrapeStatusRepository;
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
public class ZigBangRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ArticleRepository articleRepository;

	@Async("scrapeExecutor")
	public void scrapZigBang(ScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapeStatus.getRegion();
			log.info("[직방] {} 호출 중", region.getCortarName());

			String localCode = region.getCortarNo().substring(0, 8);
			ZigBangItemCatalogResponse response = zigbangApiClient.fetchItemCatalog(localCode);

			for (ZigBangItemCatalogResponse.ZigBangItemCatalog item : response.getList()) {
				Article article = ArticleDataProvider.createZigBangItemCatalog(item, region);
				articles.add(article);
			}

			articleDataProvider.saveArticles(articles);
			scrapeStatus.updateCompleted(true);

			int savedCount = articleRepository.saveAll(articles).size();
			if (savedCount != 0) {
				log.info("직방 매물 저장 완료: 지역 코드 {}, 저장된 매물 수 {}",
					scrapeStatus.getRegion().getCortarNo(), articles.size());
				Thread.sleep((long) (Math.random() * 10000));
			}
		} catch (Exception e) {
			log.error("직방 스크랩 실패: 지역 코드 {}, 에러 메시지: {}",
				scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			scrapeStatus.updateFailed(true, e.getMessage());
		}
	}
}

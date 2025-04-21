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
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ZigBangItemCatalogRequestService {

	private final ZigBangApiClient zigbangApiClient;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapZigBangItemCatalogs(ScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapeStatus.getRegion();
			String geohash = region.getGeohash();
			ZigBangItemCatalogResponse catalog = zigbangApiClient.fetchItemCatalog(geohash);

			articles.addAll(mapZigBangToArticles(catalog.getList()));
			scrapeStatus.updateCompleted(true);
		} catch (Exception e) {
			log.error("스크랩 실패 : {}", scrapeStatus.getRegion().getCortarNo(), e);
			scrapeStatus.updateFailed(true, e.getMessage());
		} finally {
			articleDataProvider.saveArticles(articles);
		}
	}

	private List<Article> mapZigBangToArticles(
		List<ZigBangItemCatalogResponse.ZigBangItemCatalog> items
	) {
		return items.stream()
			.map(ArticleDataProvider::createZigBangArticle)
			.filter(Objects::nonNull)
			.toList();
	}
}

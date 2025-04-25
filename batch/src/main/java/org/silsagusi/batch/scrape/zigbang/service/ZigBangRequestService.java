package org.silsagusi.batch.scrape.zigbang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
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
	public void scrapZigBang(ScrapeStatus scrapeStatus) throws InterruptedException {
		List<Article> articles = new ArrayList<>();
		Region region = scrapeStatus.getRegion();
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
			Thread.sleep((long) (Math.random() * 10000));
		}
	}
}

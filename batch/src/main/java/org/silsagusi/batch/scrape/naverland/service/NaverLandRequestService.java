package org.silsagusi.batch.scrape.naverland.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.scrape.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandArticleResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoMapAddressLookupService addressLookupService;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapeExecutor")
	public void scrapNaverLand(ScrapeStatus scrapeStatus) throws InterruptedException {
		List<Article> articles = new ArrayList<>();

		Region region = scrapeStatus.getRegion();

		int page = scrapeStatus.getLastScrapedPage();
		boolean hasMore;

		do {
			NaverLandArticleResponse response = naverLandApiClient.fetchArticleList(
				String.valueOf(page), region.getCenterLat().toString(), region.getCenterLon().toString(),
				region.getCortarNo());
			List<Article> pageArticles = mapNaverLandToArticles(response.getBody(), region);
			articles.addAll(pageArticles);
			scrapeStatus.updatePage(page, LocalDateTime.now(), "네이버부동산");
			hasMore = response.isMore();
			page++;
			Thread.sleep((long) (3000 + Math.random() * 4000));
		} while (hasMore);
		articleDataProvider.saveArticles(articles);
		scrapeStatus.updateCompleted(true);
	}

	private List<Article> mapNaverLandToArticles(List<NaverLandArticleResponse.NaverLandArticle> bodies, Region region) {
		return bodies.stream()
			.map(naverLandArticle -> {
				KakaoMapAddressResponse addr =
					addressLookupService.lookupAddress(naverLandArticle.getLat(), naverLandArticle.getLng());
				return ArticleDataProvider.createNaverLandArticle(naverLandArticle, region, addr);
			})
			.toList();
	}
}

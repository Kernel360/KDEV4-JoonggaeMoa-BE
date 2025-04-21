package org.silsagusi.batch.scrape.naverland.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.silsagusi.batch.infrastructure.ArticleDataProvider;
import org.silsagusi.batch.scrape.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandArticleResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.NaverLandScrapeStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandArticleRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoMapAddressLookupService addressLookupService;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapArticles(NaverLandScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapeStatus.getRegion();
			int page = scrapeStatus.getLastScrapedPage();
			boolean hasMore;

			do {
				NaverLandArticleResponse response = naverLandApiClient.fetchArticleList(
					String.valueOf(page),
					region.getCenterLat().toString(),
					region.getCenterLon().toString(),
					region.getCortarNo()
				);

				articles.addAll(mapToArticles(response.getNaverLandArticle(), region));

				page++;
				scrapeStatus.updatePage(page, LocalDateTime.now());

				hasMore = response.isMore();

				// 3~7초 랜덤 딜레이
				Thread.sleep((long)(3000 + Math.random() * 4000));
			} while (hasMore);

			// 마지막 페이지까지 완료된 경우
			scrapeStatus.updateCompleted(true);
		} catch (Exception e) {
			log.error("스크랩 실패 : {}", scrapeStatus.getRegion().getCortarNo(), e);
			scrapeStatus.updateFailed(true, e.getMessage());
		} finally {
			articleDataProvider.saveArticles(articles);
		}
	}

	private List<Article> mapToArticles(List<NaverLandArticleResponse.NaverLandArticle> bodies, Region region) {
		return bodies.stream()
			.map(naverLandArticle -> {
				KakaoMapAddressResponse addr = addressLookupService.lookupAddress(naverLandArticle.getLat(), naverLandArticle.getLng());

				if (addr == null) {
					return null;
				}

				return ArticleDataProvider.createArticle(naverLandArticle, region, addr);
			})
			.filter(Objects::nonNull)
			.toList();
	}
}
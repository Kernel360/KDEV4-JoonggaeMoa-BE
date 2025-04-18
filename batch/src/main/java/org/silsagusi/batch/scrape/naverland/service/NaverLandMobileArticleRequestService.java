package org.silsagusi.batch.scrape.naverland.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.silsagusi.batch.infrastructure.ArticleDataProvider;
import org.silsagusi.batch.scrape.naverland.client.NaverLandApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandMobileArticleResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.RegionScrapStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandMobileArticleRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoMapAddressLookupInterface addressLookupService;
	private final ArticleDataProvider articleDataProvider;

	@Async("scrapExecutor")
	public void scrapArticles(RegionScrapStatus scrapStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapStatus.getRegion();
			int page = scrapStatus.getLastScrapedPage();
			boolean hasMore;

			do {
				NaverLandMobileArticleResponse response = naverLandApiClient.fetchArticleList(
					String.valueOf(page),
					region.getCenterLat().toString(),
					region.getCenterLon().toString(),
					region.getCortarNo()
				);

				articles.addAll(mapToArticles(response.getBody(), region));

				page++;
				scrapStatus.updatePage(page, LocalDateTime.now());

				hasMore = response.isMore();

				// 3~7초 랜덤 딜레이
				Thread.sleep((long)(3000 + Math.random() * 4000));
			} while (hasMore && page <= 10);

			// 마지막 페이지까지 완료된 경우
			if (!hasMore) {
				scrapStatus.updateCompleted(true);
			}
		} catch (Exception e) {
			log.error("스크랩 실패 : {}", scrapStatus.getRegion().getCortarNo(), e);
			scrapStatus.updateFailed(true, e.getMessage());
		} finally {
			articleDataProvider.saveArticles(articles);
		}
	}

	private List<Article> mapToArticles(List<NaverLandMobileArticleResponse.Body> bodies, Region region) {
		return bodies.stream()
			.map(body -> {
				KakaoMapAddressResponse addr = addressLookupService.lookupAddress(body.getLat(), body.getLng());

				if (addr == null) {
					return null;
				}

				return ArticleDataProvider.createArticle(body, region, addr);
			})
			.filter(Objects::nonNull)
			.toList();
	}
}
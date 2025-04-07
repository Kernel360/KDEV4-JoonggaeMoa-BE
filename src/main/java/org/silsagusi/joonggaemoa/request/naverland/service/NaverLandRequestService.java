package org.silsagusi.joonggaemoa.request.naverland.service;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Article;
import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.domain.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final ArticleRepository articleRepository;

	public void scrapArticles(RegionScrapStatus scrapStatus) throws InterruptedException {
		Region region = scrapStatus.getRegion();
		int page = scrapStatus.getLastScrapedPage();
		boolean hasMore;

		do {
			ClientArticleResponse response = naverLandApiClient.fetchArticleList(
				String.valueOf(page),
				region.getCenterLat().toString(),
				region.getCenterLon().toString(),
				region.getCortarNo()
			);

			List<Article> articles = mapToArticles(response.getBody(), region);
			articleRepository.saveAll(articles);

			page++;
			scrapStatus.updatePage(page, LocalDateTime.now());

			hasMore = response.isMore();

			// 2~7초 랜덤 딜레이
			Thread.sleep((long)(Math.random() * 5000 + 2000));
		} while (hasMore && page <= 10);

		// 마지막 페이지까지 완료된 경우
		if (!hasMore) {
			scrapStatus.updateCompleted(true);
		}
	}

	private List<Article> mapToArticles(List<ClientArticleResponse.Body> bodies, Region region) {
		return bodies.stream()
			.map(body -> Article.createFrom(body, region))
			.toList();
	}
}
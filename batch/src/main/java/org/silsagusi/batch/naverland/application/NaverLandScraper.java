package org.silsagusi.batch.naverland.application;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.batch.application.ArticleValidator;
import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.naverland.infrastructure.NaverLandApiClient;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverLandScraper {

	private final NaverLandApiClient naverLandApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final ComplexMapper complexMapper;
	private final ArticleMapper articleMapper;
	private final ArticleValidator articleValidator;

	public void scrapComplex(ScrapeStatus status) throws InterruptedException {
		int page = status.getLastScrapedPage() + 1;
		boolean hasMorePages = true;
		Region region = status.getRegion();

		while (hasMorePages) {
			try {
				NaverLandComplexResponse naverLandComplexResponse = naverLandApiClient.fetchComplexList(page + "",
					region.getCenterLat(), region.getCenterLon(), region.getCortarNo());

				List<Complex> complexes = naverLandComplexResponse.getResult().stream()
					.map(response -> complexMapper.toEntity(response, region))
					.filter(articleValidator::validateExist)
					.toList();

				complexDataProvider.saveComplexes(complexes);

				hasMorePages = naverLandComplexResponse.isMore();
			} catch (Exception e) {
				log.warn(
					"Failed to scrape, platform: {}, target type: {}, region id: {}, latitude: {}, longitude: {}, page: {}, message: {}",
					ScrapeStatus.Platform.NAVERLAND, ScrapeStatus.TargetType.COMPLEX, region.getId(),
					region.getCenterLat(), region.getCenterLon(), page, e.getMessage(), e);
				Thread.sleep(300_000);
			} finally {
				status.updatePage(page++, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
			break;
		}
	}

	public void scrapArticle(ScrapeStatus status) throws InterruptedException {
		int page = status.getLastScrapedPage() + 1;
		boolean hasMorePages = true;
		Region region = status.getRegion();

		while (hasMorePages) {
			try {
				NaverLandArticleResponse naverLandArticleResponse = naverLandApiClient.fetchArticleList(page + "",
					region.getCenterLat(), region.getCenterLon(), region.getCortarNo());

				List<Article> articles = naverLandArticleResponse.getBody().stream()
					.map(response -> articleMapper.toEntity(response, region, null))
					.filter(articleValidator::validateExist)
					.toList();

				articleDataProvider.saveArticles(articles);

				hasMorePages = naverLandArticleResponse.isMore();
				log.info("naverLandArticleScraper scraped: {}", page);
			} catch (Exception e) {
				log.warn(
					"Failed to scrape, platform: {}, target type: {}, region id: {}, latitude: {}, longitude: {}, page: {}, message: {}",
					ScrapeStatus.Platform.NAVERLAND, ScrapeStatus.TargetType.ARTICLE, region.getId(),
					region.getCenterLat(), region.getCenterLon(), page, e.getMessage(), e);
				Thread.sleep(300_000);
			} finally {
				status.updatePage(page++, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
			break;
		}
	}
}

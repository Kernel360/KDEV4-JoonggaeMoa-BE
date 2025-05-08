package org.silsagusi.batch.naverland.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.application.ArticleMapper;
import org.silsagusi.batch.application.ArticleValidator;
import org.silsagusi.batch.application.ComplexMapper;
import org.silsagusi.batch.application.ComplexValidator;
import org.silsagusi.batch.infrastructure.dataprovider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataprovider.ComplexDataProvider;
import org.silsagusi.batch.naverland.infrastructure.NaverLandApiClient;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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
	private final ComplexValidator complexValidator;

	public boolean scrapComplex(ScrapeStatus status) throws InterruptedException {
		int page = status.getLastScrapedPage() + 1;
		boolean hasMore = true;
		Region region = status.getRegion();

		while (hasMore) {
			try {
				NaverLandComplexResponse naverLandComplexResponse = naverLandApiClient.fetchComplexList(page + "",
					region.getCenterLat(), region.getCenterLon(), region.getCortarNo());

				if (naverLandComplexResponse == null || naverLandComplexResponse.getResult() == null) {
					page++;
					continue;
				}

				List<Complex> complexes = naverLandComplexResponse.getResult().stream()
					.map(response -> complexMapper.toEntity(response, region))
					.filter(complexValidator::validateExist)
					.toList();

				complexDataProvider.saveComplexes(complexes);

				hasMore = naverLandComplexResponse.isMore();
			} catch (Exception e) {
				log.error(
					"스크래핑 실패: {} - {}. region_id: {} - {}페이지. 좌표값: {},{}. 에러 내용: {}",
					Platform.NAVERLAND, ScrapeTargetType.COMPLEX, region.getId(), page,
					region.getCenterLat(), region.getCenterLon(), e.getMessage(), e);
				Thread.sleep(600_000);
			} finally {
				status.updatePage(page++, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
		}
		return true;
	}

	public boolean scrapArticle(ScrapeStatus status) throws InterruptedException {
		int page = status.getLastScrapedPage() + 1;
		boolean hasMore = true;
		Region region = status.getRegion();

		while (hasMore) {
			try {
				NaverLandArticleResponse naverLandArticleResponse = naverLandApiClient.fetchArticleList(page + "",
					region.getCenterLat(), region.getCenterLon(), region.getCortarNo());

				List<Article> articles = naverLandArticleResponse.getBody().stream()
					.map(response -> articleMapper.toEntity(response, region, null))
					.filter(articleValidator::validateExist)
					.toList();

				articleDataProvider.saveArticles(articles);

				hasMore = naverLandArticleResponse.isMore();
			} catch (Exception e) {
				log.error(
					"스크래핑 실패: {} - {}. region_id: {} - {}페이지. 좌표값: {},{}. 에러 내용: {}",
					Platform.NAVERLAND, ScrapeTargetType.ARTICLE, region.getId(), page,
					region.getCenterLat(), region.getCenterLon(), e.getMessage(), e);
				Thread.sleep(600_000);
			} finally {
				status.updatePage(page++, LocalDateTime.now());
				Thread.sleep((long)(3000 + Math.random() * 4000));
			}
		}
		return true;
	}
}

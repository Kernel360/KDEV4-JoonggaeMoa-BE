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
	public void scrapNaverLand(ScrapeStatus scrapeStatus) {
		List<Article> articles = new ArrayList<>();

		try {
			Region region = scrapeStatus.getRegion();

			if (!"sec".equals(region.getCortarType())) {
				return;
			}

			int page = scrapeStatus.getLastScrapedPage();
			boolean hasMore;
			int totalFetched = 0;

			do {
				NaverLandArticleResponse nlrResponse = naverLandApiClient.fetchArticleList(
					String.valueOf(page),
					region.getCenterLat().toString(),
					region.getCenterLon().toString(),
					region.getCortarNo()
				);

				List<Article> pageArticles = mapNaverLandToArticles(nlrResponse.getBody(), region);
				articles.addAll(pageArticles);
				totalFetched += pageArticles.size();

				scrapeStatus.updatePage(page, LocalDateTime.now(), "네이버부동산");
				hasMore = nlrResponse.isMore();
				page++;

				Thread.sleep((long) (3000 + Math.random() * 4000));
			} while (hasMore);

			try {
				articleDataProvider.saveArticles(articles);
			} catch (Exception e) {
				log.error("네이버 부동산 매물 저장 실패: 지역 코드 {}, 에러 메시지: {}",
					scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
				throw e; // 예외를 다시 던져서 트랜잭션 롤백
			}

			// 마지막 페이지까지 완료된 경우
			scrapeStatus.updateCompleted(true);
			log.info("네이버 부동산 스크랩 완료: 지역 코드 {}, 총 처리된 매물 수 {}, 총 페이지 수 {}",
				scrapeStatus.getRegion().getCortarNo(), totalFetched, page - 1);

		} catch (Exception e) {
			log.error("네이버 부동산 스크랩 실패: 지역 코드 {}, 에러 메시지: {}",
				scrapeStatus.getRegion().getCortarNo(), e.getMessage(), e);
			scrapeStatus.updateFailed(true, e.getMessage());

			// 예외를 다시 던져서 트랜잭션 롤백
			throw new RuntimeException("네이버 부동산 스크랩 실패", e);
		}
	}

	private List<Article> mapNaverLandToArticles(
		List<NaverLandArticleResponse.NaverLandArticle> bodies,
		Region region
	) {
		return bodies.stream()
			.map(naverLandArticle -> {
				KakaoMapAddressResponse addr =
					addressLookupService.lookupAddress(
						naverLandArticle.getLat(),
						naverLandArticle.getLng()
					);

				if (addr == null) {
					return null;

				}
				return ArticleDataProvider.createNaverLandArticle(
					naverLandArticle, region, addr
				);
			}).filter(Objects::nonNull)
			.toList();
	}
}

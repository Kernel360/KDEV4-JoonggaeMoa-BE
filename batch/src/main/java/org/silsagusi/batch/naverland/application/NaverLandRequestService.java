package org.silsagusi.batch.naverland.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.silsagusi.batch.infrastructure.dataprovider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataprovider.ComplexDataProvider;
import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
import org.silsagusi.batch.infrastructure.repository.RegionRepository;
import org.silsagusi.batch.naverland.infrastructure.NaverLandApiClient;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandScrapeRequest;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandScrapeResult;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverLandRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;
	private final RegionRepository regionRepository;

	// Scheduler for delayed completion
	private static final ScheduledExecutorService scheduler =
		Executors.newSingleThreadScheduledExecutor();

	@Async("scrapeExecutor")
	public CompletableFuture<NaverLandScrapeResult> scrapNaverLand(NaverLandScrapeRequest request) {
		CompletableFuture<NaverLandScrapeResult> future = new CompletableFuture<>();
		int page = request.getLastScrapedPage();
		boolean hasMore;

		Set<String> seenComplexNos = new HashSet<>();
		Map<String, Complex> codeToComplex = new HashMap<>();

		try {
			do {
				Region region = regionRepository.findById(request.getRegionId())
					.orElseThrow(() -> new IllegalArgumentException(request.getRegionId() + "지역 없음"));

				// 단지 스크래핑
				NaverLandComplexResponse compResp = null;
				// NaverLandComplexResponse compResp = naverLandApiClient.fetchComplexList(
				// 	String.valueOf(page), request.getCenterLat().toString(),
				// 	request.getCenterLon().toString(), request.getCortarNo()
				// );
				Thread.sleep((long)(2000 + Math.random() * 3000)); // 2~5초 랜덤 대기

				// 중복 없는 단지만 변환 후 저장 리스트에 추가
				List<Complex> allComplexes = new ArrayList<>();
				for (NaverLandComplexResponse.NaverLandComplex dto : compResp.getResult()) {
					if (seenComplexNos.add(dto.getHscpNo())) {
						Complex domain = complexDataProvider.createNaverLandComplex(dto, region);
						allComplexes.add(domain);
						codeToComplex.put(dto.getHscpNo(), domain);
					}
				}
				complexDataProvider.saveComplexes(allComplexes);

				// 매물 스크래핑
				NaverLandArticleResponse artResp = null;
				// NaverLandArticleResponse artResp = naverLandApiClient.fetchArticleList(
				// 	String.valueOf(page), request.getCenterLat().toString(),
				// 	request.getCenterLon().toString(), request.getCortarNo());
				List<Article> pageArticles = mapNaverLandToArticles(artResp.getBody(), region, codeToComplex);
				articleDataProvider.saveArticles(pageArticles);
				Thread.sleep((long)(2000 + Math.random() * 3000)); // 2~5초 랜덤 대기

				hasMore = artResp.isMore() && page < 20;
				page++;
				Thread.sleep((long)(10000 + Math.random() * 5000)); // 10~15초 랜덤 대기
			} while (hasMore);

			long delay = (long)(2000 + Math.random() * 3000);
			int finalPage = page;
			scheduler.schedule(() ->
					future.complete(new NaverLandScrapeResult(request.getScrapeStatusId(), finalPage, null)),
				delay, TimeUnit.MILLISECONDS
			);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.debug("{} - {}페이지 스크래핑 중단됨. 현재 스크래핑된 값까지 저장중...",
				request.getRegionId(), page - 1, e);
			future.complete(new NaverLandScrapeResult(request.getScrapeStatusId(), page, e.getMessage()));
		} catch (NullPointerException e) {
			log.debug("서비스 이용 제한. https://m.land.naver.com 에서 캡챠 인증 후 사용 가능");
			future.complete(new NaverLandScrapeResult(request.getScrapeStatusId(), page, "CAPTCHA"));
		} catch (Exception e) {
			log.error("에러 발생. {} - {}", request.getRegionId(), e.getMessage(), e);
			future.complete(new NaverLandScrapeResult(request.getScrapeStatusId(), page, e.getMessage()));
		}
		return future;
	}

	private List<Article> mapNaverLandToArticles(
		List<NaverLandArticleResponse.NaverLandArticle> articleList,
		Region region, Map<String, Complex> codeToComplex) {
		return articleList.stream()
			.map(article -> {
				AddressResponse kakaoResp = kakaoMapApiClient.lookupAddress(article.getLng(), article.getLat());
				Complex complex = codeToComplex.get(article.getAtclNm());
				return articleDataProvider.createNaverLandArticle(article, kakaoResp, region, complex);
			})
			.toList();
	}
}

package org.silsagusi.batch.naverland.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.silsagusi.batch.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.batch.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.batch.infrastructure.external.AddressResponse;
import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
import org.silsagusi.batch.naverland.infrastructure.NaverLandApiClient;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.ScrapeStatus;
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

	@Async("scrapeExecutor")
	public void scrapNaverLand(ScrapeStatus scrapeStatus) {
		Region region = scrapeStatus.getRegion();
		int page = scrapeStatus.getLastScrapedPage();
		boolean hasMore;

		Set<String> seenComplexNos = new HashSet<>();

		Map<String, Complex> codeToComplex = new HashMap<>();

		try {
			do {
				// 단지 스크래핑
				NaverLandComplexResponse compResp = naverLandApiClient.fetchComplexList(
					String.valueOf(page), region.getCenterLat().toString(),
					region.getCenterLon().toString(), region.getCortarNo()
				);
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
				NaverLandArticleResponse artResp = naverLandApiClient.fetchArticleList(
					String.valueOf(page), region.getCenterLat().toString(),
					region.getCenterLon().toString(), region.getCortarNo());
				List<Article> pageArticles = mapNaverLandToArticles(artResp.getBody(), region, codeToComplex);
				articleDataProvider.saveArticles(pageArticles);
				Thread.sleep((long)(2000 + Math.random() * 3000)); // 2~5초 랜덤 대기

				scrapeStatus.updatePage(page, LocalDateTime.now(), "네이버 부동산");
				hasMore = artResp.isMore() && page < 20;
				page++;
				Thread.sleep((long)(10000 + Math.random() * 5000)); // 10~15초 랜덤 대기
			} while (hasMore);

			scrapeStatus.completed();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.debug("{} - {}페이지 스크래핑 중단됨. 현재 스크래핑된 값까지 저장중...",
				scrapeStatus.getRegion().getId(), page - 1, e);
		} catch (NullPointerException e) {
			log.debug("서비스 이용 제한. https://m.land.naver.com 에서 캡챠 인증 후 사용 가능");
			scrapeStatus.failed("CAPTCHA 걸림");
		} catch (Exception e) {
			log.error("에러 발생. {} - {}", scrapeStatus.getRegion().getId(), e.getMessage(), e);
			scrapeStatus.failed(e.getMessage());
		}
	}

	private List<Article> mapNaverLandToArticles(
		List<NaverLandArticleResponse.NaverLandArticle> articleList,
		Region region, Map<String, Complex> codeToComplex) {
		return articleList.stream()
			.map(article -> {
				AddressResponse kakaoResp = kakaoMapApiClient.lookupAddress(article.getLng(), article.getLat());
				Complex complex = codeToComplex.get(article.getCortarNo());
				return articleDataProvider.createNaverLandArticle(article, kakaoResp, region, complex);
			})
			.toList();
	}
}

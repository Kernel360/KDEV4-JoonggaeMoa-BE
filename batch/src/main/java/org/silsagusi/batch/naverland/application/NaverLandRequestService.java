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
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverLandRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final KakaoMapApiClient kakaoMapApiClient;
	private final ArticleDataProvider articleDataProvider;
	private final ComplexDataProvider complexDataProvider;

	@Async("scrapeExecutor")
	public void scrapNaverLand(ScrapeStatus scrapeStatus) throws InterruptedException {
		Region region = scrapeStatus.getRegion();
		int page = scrapeStatus.getLastScrapedPage();
		boolean hasMore;

		List<Article> allArticles = new ArrayList<>();
		List<Complex> allComplexes = new ArrayList<>();
		Set<String> seenComplexNos = new HashSet<>();

		Map<String, Complex> codeToComplex = new HashMap<>();

		do {
			// 매물 스크래핑
			NaverLandArticleResponse artResp = naverLandApiClient.fetchArticleList(
				String.valueOf(page), region.getCenterLat().toString(), region.getCenterLon().toString(),
				region.getCortarNo());
			List<Article> pageArticles = mapNaverLandToArticles(artResp.getBody(), region, codeToComplex);
			allArticles.addAll(pageArticles);
			Thread.sleep((long)(3000 + Math.random() * 4000));

			// 단지 스크래핑
			NaverLandComplexResponse compResp = naverLandApiClient.fetchComplexList(
				String.valueOf(page), region.getCenterLat().toString(), region.getCenterLon().toString(),
				region.getCortarNo()
			);
			// 중복 없는 단지만 변환 후 저장 리스트에 추가
			for (NaverLandComplexResponse.NaverLandComplex dto : compResp.getResult()) {
				if (seenComplexNos.add(dto.getHscpNo())) {
					Complex domain = complexDataProvider.createNaverLandComplex(dto, region);
					allComplexes.add(domain);
					codeToComplex.put(dto.getHscpNo(), domain);
				}
			}

			scrapeStatus.updatePage(page, LocalDateTime.now(), "네이버부동산");
			hasMore = artResp.isMore();
			page++;
			Thread.sleep((long)(3000 + Math.random() * 4000));
		} while (hasMore);
		articleDataProvider.saveArticles(allArticles);
		complexDataProvider.saveComplexes(allComplexes);
		scrapeStatus.completed();
	}

	private List<Article> mapNaverLandToArticles(
		List<NaverLandArticleResponse.NaverLandArticle> articleList,
		Region region, Map<String, Complex> codeToComplex) {
		return articleList.stream()
			.map(article -> {
				AddressResponse kakaoResp = kakaoMapApiClient.lookupAddress(article.getLat(), article.getLng());
				Complex complex = codeToComplex.get(article.getCortarNo());
				return articleDataProvider.createNaverLandArticle(article, kakaoResp, region, complex);
			})
			.toList();
	}
}

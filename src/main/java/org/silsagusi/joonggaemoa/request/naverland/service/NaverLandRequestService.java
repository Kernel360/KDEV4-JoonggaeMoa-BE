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
	private final ComplexRepository complexRepository;
	private final RegionRepository regionRepository;

	// public void scrap() throws InterruptedException {
	// 	List<Region> regions = regionRepository.findAll();
	//
	// 	// 매물 정보 수집
	// 	log.info("네이버 부동산 매물 정보 수집 시작");
	// 	for (Region region : regions) {
	// 		scrapArticlesForRegion(region);
	// 	}
	//
	// 	// 매물 정보 수집 후 단지 정보 수집
	// 	log.info("네이버 부동산 단지 정보 수집 시작");
	// 	for (Region region : regions) {
	// 		scrapComplexForRegion(region);
	// 	}
	// }
	//
	// private void scrapArticlesForRegion(Region region) throws InterruptedException {
	// 	String lat = region.getCenterLat().toString();
	// 	String lon = region.getCenterLon().toString();
	// 	String cortarNo = region.getCortarNo();
	// 	int page = 1;
	// 	ClientArticleResponse clientArticleResponse;
	// 	do {
	// 		clientArticleResponse = naverLandApiClient.fetchArticleList(page + "", lat, lon, cortarNo);
	// 		log.info(clientArticleResponse.getBody().toString());
	//
	// 		List<Article> articleList = clientArticleResponse.getBody().stream()
	// 			.map(article -> new Article(
	// 				article.getAtclNm(),
	// 				article.getRletTpNm(),
	// 				article.getTradTpNm(),
	// 				article.getHanPrc(),
	// 				article.getAtclCfmYmd(),
	// 				article.getLat(),
	// 				article.getLng()
	// 			))
	// 			.toList();
	// 		articleRepository.saveAll(articleList);
	// 		log.info("매물 정보 수집 : {}", articleList);
	//
	// 		// 2초 ~ 7초 랜덤 대기
	// 		Thread.sleep((long)(Math.random() * 5000 + 2000));
	// 		page++;
	// 	} while (clientArticleResponse.isMore());
	// }
	//
	// private void scrapComplexForRegion(Region region) throws InterruptedException {
	// 	String lat = region.getCenterLat().toString();
	// 	String lon = region.getCenterLon().toString();
	// 	String cortarNo = region.getCortarNo();
	// 	int page = 1;
	// 	ClientComplexResponse clientComplexResponse;
	// 	do {
	// 		clientComplexResponse = naverLandApiClient.fetchComplexList(page + "", lat, lon, cortarNo);
	// 		log.info(clientComplexResponse.getResult().toString());
	//
	// 		List<Complex> complexList = clientComplexResponse.getResult().stream()
	// 			.map(complex -> new Complex(
	// 				complex.getHscpNm(),
	// 				complex.getHscpTypeNm(),
	// 				complex.getUseAprvYmd()
	// 			))
	// 			.toList();
	// 		complexRepository.saveAll(complexList);
	// 		log.info("단지 정보 수집 : {}", complexList);
	//
	// 		Thread.sleep((long)(Math.random() * 5000));
	// 		page++;
	// 	} while (clientComplexResponse.isMore());
	// }

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

			List<Article> articles = mapToArticles(response.getBodies());
			articleRepository.saveAll(articles);

			page++;
			scrapStatus.updatePage(page, LocalDateTime.now());

			hasMore = response.isMore();

			// 2~5초 랜덤 딜레이
			Thread.sleep((long)(Math.random() * 5000 + 2000));
		} while (hasMore && page <= 10);

		// 마지막 페이지까지 완료된 경우
		if (!hasMore) {
			scrapStatus.updateCompleted(true);
		}
	}

	private List<Article> mapToArticles(List<ClientArticleResponse.Body> bodies) {
		return bodies.stream()
			.map(Article::createFrom)
			.toList();
	}
}
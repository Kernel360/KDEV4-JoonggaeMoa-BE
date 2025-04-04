package org.silsagusi.joonggaemoa.request.naverland.service;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Article;
import org.silsagusi.joonggaemoa.domain.article.entity.Complex;
import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.ComplexRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientArticleResponse;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientComplexResponse;
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

	/* 법정동 코드
		법정동코드	법정동명
		1111000000	서울특별시 종로구
		1114000000	서울특별시 중구
		1117000000	서울특별시 용산구
		1120000000	서울특별시 성동구
		1121500000	서울특별시 광진구
		1123000000	서울특별시 동대문구
		1126000000	서울특별시 중랑구
		1129000000	서울특별시 성북구
		1130500000	서울특별시 강북구
		1132000000	서울특별시 도봉구
		1135000000	서울특별시 노원구
		1138000000	서울특별시 은평구
		1141000000	서울특별시 서대문구
		1144000000	서울특별시 마포구
		1147000000	서울특별시 양천구
		1150000000	서울특별시 강서구
		1153000000	서울특별시 구로구
		1154500000	서울특별시 금천구
		1156000000	서울특별시 영등포구
		1159000000	서울특별시 동작구
		1162000000	서울특별시 관악구
		1165000000	서울특별시 서초구
		1168000000	서울특별시 강남구
		1171000000	서울특별시 송파구
		1174000000	서울특별시 강동구
	 */
	public static final String[] cortar = {
		"1111000000",
		"1114000000",
		"1117000000",
		"1120000000",
		"1121500000",
		"1123000000",
		"1126000000",
		"1129000000",
		"1130500000",
		"1132000000",
		"1135000000",
		"1138000000",
		"1141000000",
		"1144000000",
		"1147000000",
		"1150000000",
		"1153000000",
		"1154500000",
		"1156000000",
		"1159000000",
		"1162000000",
		"1165000000",
		"1168000000",
		"1171000000",
		"1174000000",
	};

	public void scrap() throws InterruptedException {
		// 법정동 코드 수집
		// for (String s : cortar) {
		//     scrapByCortar(s);
		// }

		List<Region> regions = regionRepository.findAll();

		// 매물 정보 수집
		for (Region region : regions) {
			scrapArticlesForRegion(region);
		}

		// 매물 정보 수집 후 단지 정보 수집
		log.info("네이버 부동산 단지 정보 수집 시작");
		for (Region region : regions) {
			scrapComplexForRegion(region);
		}
	}

	private void scrapArticlesForRegion(Region region) throws InterruptedException {
		String lat = region.getCenterLat().toString();
		String lon = region.getCenterLon().toString();
		String cortarNo = region.getCortarNo();
		int page = 1;
		ClientArticleResponse clientArticleResponse;
		do {
			clientArticleResponse = naverLandApiClient.fetchArticleList(page + "", lat, lon, cortarNo);
			log.info(clientArticleResponse.getBody().toString());

			List<Article> articleList = clientArticleResponse.getBody().stream()
				.map(article -> new Article(
					article.getAtclNm(),
					article.getRletTpNm(),
					article.getTradTpNm(),
					article.getHanPrc(),
					article.getAtclCfmYmd(),
					article.getLat(),
					article.getLng()
				))
				.toList();
			articleRepository.saveAll(articleList);
			log.info("매물 정보 수집 : {}", articleList);

			// 2초 ~ 7초 랜덤 대기
			Thread.sleep((long)(Math.random() * 5000 + 2000));
			page++;
		} while (clientArticleResponse.isMore());
	}

	private void scrapComplexForRegion(Region region) throws InterruptedException {
		String lat = region.getCenterLat().toString();
		String lon = region.getCenterLon().toString();
		String cortarNo = region.getCortarNo();
		int page = 1;
		ClientComplexResponse clientComplexResponse;
		do {
			clientComplexResponse = naverLandApiClient.fetchComplexList(page + "", lat, lon, cortarNo);
			log.info(clientComplexResponse.getResult().toString());

			List<Complex> complexList = clientComplexResponse.getResult().stream()
				.map(complex -> new Complex(
					complex.getHscpNm(),
					complex.getHscpTypeNm(),
					complex.getUseAprvYmd()
				))
				.toList();
			complexRepository.saveAll(complexList);
			log.info("단지 정보 수집 : {}", complexList);

			Thread.sleep((long)(Math.random() * 5000));
			page++;
		} while (clientComplexResponse.isMore());
	}

	// private void scrapByCortar(String cortarNo) {
	//     ClientRegionResponse clientRegionResponse = naverLandApiClient.fetchRegionList(cortarNo);
	//
	//     List<Region> regionList = clientRegionResponse.getRegionList().stream()
	//         .map(region -> new Region(
	//             region.getCortarNo(),
	//             region.getCenterLat(),
	//             region.getCenterLon(),
	//             region.getCortarName(),
	//             region.getCortarType()
	//         ))
	//         .toList();
	//     regionRepository.saveAll(regionList);
	//     log.info("법정동 코드 수집 : {}", regionList);
	// }
}

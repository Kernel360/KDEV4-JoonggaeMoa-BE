package org.silsagusi.batch.infrastructure;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandMobileArticleResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	public static Article createArticle(
		NaverLandMobileArticleResponse.Body body,
		Region region,
		KakaoMapAddressResponse kakaoMapAddressResponse
	) {
		return new Article(
			body.getAtclNo(),
			body.getCortarNo(),
			body.getAtclNm(),
			body.getRletTpNm(),
			body.getTradTpNm(),
			body.getFlrInfo(),
			body.getPrc(),
			body.getRentPrc(),
			body.getSpc1(),
			body.getSpc2(),
			body.getDirection(),
			parseConfirmedAt(body.getAtclCfmYmd()),
			body.getRepImgUrl(),
			body.getLat(),
			body.getLng(),
			body.getAtclFetrDesc(),
			body.getTagList(),
			body.getCpNm(),
			body.getRltrNm(),
			body.getSbwyInfo(),
			body.getTradeCheckedByOwner(),
			region,
			kakaoMapAddressResponse.getLotAddress(),
			kakaoMapAddressResponse.getRoadAddress(),
			kakaoMapAddressResponse.getCity(),
			kakaoMapAddressResponse.getDistrict(),
			kakaoMapAddressResponse.getRegion(),
			kakaoMapAddressResponse.getMainAddressNo(),
			kakaoMapAddressResponse.getSubAddressNo(),
			kakaoMapAddressResponse.getRoadName(),
			kakaoMapAddressResponse.getMainBuildingNo(),
			kakaoMapAddressResponse.getSubBuildingNo(),
			kakaoMapAddressResponse.getBuildingName(),
			kakaoMapAddressResponse.getZoneNo()
		);
	}

	private static LocalDate parseConfirmedAt(String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return null;
		try {
			return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yy.MM.dd."));
		} catch (Exception e) {
			try {
				return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
			} catch (Exception ignored) {
				return null;
			}
		}
	}
}

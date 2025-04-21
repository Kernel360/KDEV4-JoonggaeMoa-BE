package org.silsagusi.batch.infrastructure;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandArticleResponse;
import org.silsagusi.batch.scrape.zigbang.service.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	public static Article createNaverLandArticle(
		NaverLandArticleResponse.NaverLandArticle naverLandArticle,
		Region region,
		KakaoMapAddressResponse kakaoMapAddressResponse
	) {
		Article article = new Article(
			naverLandArticle.getAtclNo(),
			naverLandArticle.getCortarNo(),
			naverLandArticle.getAtclNm(),
			naverLandArticle.getRletTpNm(),
			naverLandArticle.getTradTpNm(),
			naverLandArticle.getFlrInfo(),
			naverLandArticle.getPrc(),
			naverLandArticle.getRentPrc(),
			naverLandArticle.getSpc1(),
			naverLandArticle.getSpc2(),
			naverLandArticle.getDirection(),
			parseConfirmedAt(naverLandArticle.getAtclCfmYmd()),
			naverLandArticle.getRepImgUrl(),
			naverLandArticle.getLat(),
			naverLandArticle.getLng(),
			naverLandArticle.getAtclFetrDesc(),
			naverLandArticle.getCpNm(),
			naverLandArticle.getRltrNm(),
			naverLandArticle.getMinMviFee(),
			naverLandArticle.getMaxMviFee(),
			naverLandArticle.getSbwyInfo(),
			naverLandArticle.getSvcCont(),
			naverLandArticle.getGdrNm(),
			naverLandArticle.getEtRoomCnt(),
			naverLandArticle.getTradeCheckedByOwner(),
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

		return article;
	}

	public static Article createZigBangArticle(
		ZigBangItemCatalogResponse.ZigBangItemCatalog item
	) {
		Article article = new Article(
			String.valueOf(item.getAreaHoId()),
			null,
			item.getAreaDanjiName() + " " + item.getDong(),
			"아파트",
			mapTranType(item.getTranType()),
			item.getFloor(),
			item.getDepositMin(),
			item.getRentMin(),
			item.getRoomTypeTitle().getM2(),
			String.valueOf(item.getSizeM2()),
			null,
			null,
			item.getThumbnailUrl(),
			null,
			null,
			item.getItemTitle(),
			mapItemType(item.getItemType()), // 거래유형
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null
		);
		return article;
	}

	private static String mapTranType(String tranType) {
		if (Objects.equals(tranType, "trade")) {
			return "매매";
		} else if (Objects.equals(tranType, "charter")) {
			return "전세";
		} else if (Objects.equals(tranType, "rental")) {
			return "월세";
		} else {
			return null;
		}
	}

	private static String mapItemType(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return "직방(제휴 매물)"; // 직방 제휴 매물
		} else if (Objects.equals(itemType, "self_ad")) {
			return "직방(개인 매물)"; // 개인 매물, 유료 광고 적용됨
		} else {
			return "직방";
		}
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

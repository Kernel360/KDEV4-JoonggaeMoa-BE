package org.silsagusi.batch.application;

import java.util.Objects;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandArticleResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangItemCatalogResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

	public Article toEntity(NaverLandArticleResponse.NaverLandArticle naverLandArticle, Region region,
		Complex complex) {
		return Article.create(
			naverLandArticle.getAtclNo(),
			naverLandArticle.getCortarNo(),
			naverLandArticle.getAtclNm(),
			naverLandArticle.getRletTpCd(),
			naverLandArticle.getRletTpNm(),
			naverLandArticle.getTradTpNm(),
			naverLandArticle.getFlrInfo(),
			naverLandArticle.getPrc(),
			naverLandArticle.getRentPrc(),
			naverLandArticle.getSpc1(),
			naverLandArticle.getSpc2(),
			naverLandArticle.getDirection(),
			naverLandArticle.getAtclCfmYmd(),
			naverLandArticle.getRepImgUrl() == null ? null :
				"https://landthumb-phinf.pstatic.net" + naverLandArticle.getRepImgUrl(),
			naverLandArticle.getLat(),
			naverLandArticle.getLng(),
			naverLandArticle.getAtclFetrDesc(),
			naverLandArticle.getCpNm(),
			naverLandArticle.getRltrNm(),
			naverLandArticle.getSbwyInfo(),
			naverLandArticle.getTradeCheckedByOwner(),
			createPoint(naverLandArticle.getLat(), naverLandArticle.getLng()),
			region,
			complex
		);
	}

	public Article toEntity(ZigBangItemCatalogResponse.ZigBangItemCatalog zigbangItem, Region region, Complex complex) {
		return Article.create(
			String.valueOf(zigbangItem.getAreaHoId()),
			region.getCortarNo(),
			zigbangItem.getAreaDanjiName() + " " + zigbangItem.getDong(),
			"A01",
			"아파트",
			mapTranType(zigbangItem.getTranType()),
			zigbangItem.getFloor(),
			zigbangItem.getDepositMin(),
			zigbangItem.getRentMin(),
			zigbangItem.getRoomTypeTitle().getM2(),
			String.valueOf(zigbangItem.getSizeM2()),
			null,
			complex.getConfirmedAt(),
			Objects.equals(zigbangItem.getThumbnailUrl(), "") ? null :
				zigbangItem.getThumbnailUrl() + "?w=1000",
			complex.getLatitude(),
			complex.getLongitude(),
			zigbangItem.getItemTitle(),
			mapItemType(zigbangItem.getItemType()),
			"직방",
			null,
			mapIsChecked(zigbangItem.getItemType()),
			createPoint(complex.getLatitude(), complex.getLongitude()),
			region,
			complex
		);
	}

	// 거래 유형
	private String mapTranType(String tranType) {
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

	// 매물 출처
	private String mapItemType(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return "직방 파트너 계약 매물"; // 직방 제휴 매물
		} else if (Objects.equals(itemType, "self_ad")) {
			return "직방 개인 광고 매물"; // 개인 매물, 유료 광고 적용됨
		} else {
			return "직방 검증 완료 매물";
		}
	}

	// 확인매물여부
	private Boolean mapIsChecked(String itemType) {
		if (Objects.equals(itemType, "partner")) {
			return true; // 직방 제휴 매물
		} else
			return Objects.equals(itemType, "self_ad"); // 개인 매물, 유료 광고 적용됨
	}

	private Point createPoint(Double lat, Double lng) {
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		return geometryFactory.createPoint(new Coordinate(lng, lat));
	}
}

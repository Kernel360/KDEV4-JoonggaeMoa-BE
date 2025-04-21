package org.silsagusi.api.article.application.dto;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.core.domain.article.Article;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponse {
	private Long id;
	private String articleCode;
	private String dongCode;
	private String articleName;
	private String articleType;
	private String tradeType;
	private String floors;
	private Integer priceSale;
	private Integer priceRent;
	private String areaSupply;
	private String areaExclusive;
	private String direction;
	private LocalDate confirmedAt;
	private String imageUrl;
	private Double latitude;
	private Double longitude;
	private String atclFetrDesc;
	private List<String> tags;
	private String companyName;
	private String agentName;
	private String subwayInfo;
	private Boolean isChecked;
	private String lotAddress;
	private String roadAddress;
	private String city;
	private String district;
	private String town;
	private String mainAddressNo;
	private String subAddressNo;
	private String roadName;
	private String mainBuildingNo;
	private String subBuildingNo;
	private String buildingName;
	private String zipCode;

	public static ArticleResponse of(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.articleCode(article.getArticleCode())
			.dongCode(article.getDongCode())
			.articleName(article.getArticleName())
			.articleType(article.getArticleType())
			.tradeType(article.getTradeType())
			.floors(article.getFloors())
			.priceSale(article.getPriceSale())
			.priceRent(article.getPriceRent())
			.areaSupply(article.getAreaSupply())
			.areaExclusive(article.getAreaExclusive())
			.direction(article.getDirection())
			.confirmedAt(article.getConfirmedAt())
			.imageUrl(article.getImageUrl())
			.latitude(article.getLatitude())
			.longitude(article.getLongitude())
			.atclFetrDesc(article.getArticleDesc())
			.tags(article.getTags().stream().map(
				articleTag -> articleTag
					.getTag()
					.getName())
				.toList())
			.companyName(article.getCompanyName())
			.agentName(article.getAgency())
			.subwayInfo(article.getSubway())
			.isChecked(article.getIsChecked())
			.lotAddress(article.getAddressFullLot())
			.roadAddress(article.getAddressFullRoad())
			.city(article.getAddress1SiDo())
			.district(article.getAddress2SiGunGu())
			.town(article.getAddress3DongEupMyeon())
			.mainAddressNo(article.getAddressFullLotNo1())
			.subAddressNo(article.getAddressFullLotNo2())
			.roadName(article.getAddressFullRoadName())
			.mainBuildingNo(article.getAddressFullRoadNo1())
			.subBuildingNo(article.getAddressFullRoadNo2())
			.buildingName(article.getAddressBuildingName())
			.zipCode(article.getAddressZipCode())
			.build();
	}
}

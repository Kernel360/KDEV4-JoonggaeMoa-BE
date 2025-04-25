package org.silsagusi.api.article.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.core.domain.article.Article;

import java.time.LocalDate;

@Getter
@Builder
public class ArticleResponse {
	private Long id;
	private String articleCode;
	private String bjdCode;
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
	private String articleDesc;
	private String companyName;
	private String agency;
	private Integer priceRoomMin;
	private Integer priceRoomMax;
	private String subway;
	private String articleDescRoom;
	private String articleDescMw;
	private Integer emptyRoomCount;
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
			.bjdCode(article.getBjdCode())
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
			.articleDesc(article.getArticleDesc())
			.companyName(article.getCompanyName())
			.agency(article.getAgency())
			.priceRoomMin(article.getPriceRoomMin())
			.priceRoomMax(article.getPriceRoomMax())
			.subway(article.getSubway())
			.articleDescRoom(article.getArticleDescRoom())
			.articleDescMw(article.getArticleDescMw())
			.emptyRoomCount(article.getEmptyRoomCount())
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

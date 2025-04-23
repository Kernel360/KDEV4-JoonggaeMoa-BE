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
	private String buildingTypeCode;
	private String buildingType;
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
//	private Integer priceRoomMin;
//	private Integer priceRoomMax;
	private String subway;
//	private String articleDescRoom;
//	private String articleDescMw;
//	private Integer emptyRoomCount;
	private Boolean isChecked;
	private String addressFullLot;
	private String addressFullRoad;
	private String address1SiDo;
	private String address2SiGunGu;
	private String address3DongEupMyeon;

	// TODO : 이거 무엇에 쓰는 물건잉교
	public static ArticleResponse of(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.articleCode(article.getArticleCode())
			.bjdCode(article.getBjdCode())
			.articleName(article.getArticleName())
			.buildingTypeCode(article.getBuildingTypeCode())
			.buildingType(article.getBuildingType())
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
			.subway(article.getSubway())
			.isChecked(article.getIsChecked())
			.addressFullLot(article.getAddressFullLot())
			.addressFullRoad(article.getAddressFullRoad())
			.address1SiDo(article.getAddress1SiDo())
			.address2SiGunGu(article.getAddress2SiGunGu())
			.address3DongEupMyeon(article.getAddress3DongEupMyeon())
			.build();
	}
}

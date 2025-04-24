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
}

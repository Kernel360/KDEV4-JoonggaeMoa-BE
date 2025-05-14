package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkerFilterRequest {
	private String tradeType;
	private String buildingTypeCode;
	private Long minSalePrice;
	private Long maxSalePrice;
	private Long minRentPrice;
	private Long maxRentPrice;
}

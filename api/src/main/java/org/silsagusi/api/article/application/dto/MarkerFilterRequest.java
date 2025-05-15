package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MarkerFilterRequest {
	private List<String> tradeType;
	private List<String> buildingTypeCode;
	private Long minSalePrice;
	private Long maxSalePrice;
	private Long minRentPrice;
	private Long maxRentPrice;
}

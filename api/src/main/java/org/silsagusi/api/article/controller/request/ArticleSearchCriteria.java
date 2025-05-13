package org.silsagusi.api.article.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class ArticleSearchCriteria {
	private List<String> realEstateType;
	private List<String> tradeType;
	private String minPrice;
	private String maxPrice;
	private String regionPrefix;
	private Double neLat;
	private Double neLng;
	private Double swLat;
	private Double swLng;
}

package org.silsagusi.api.article.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponse {
	private Long id;
	private Double latitude;
	private Double longitude;
	private String cortarNo;
	private String articleNo;
	private String name;
	private String realEstateType;
	private String tradeType;
	private String price;
	private Integer rentPrice;
	private LocalDate confirmedAt;
	private String lotAddressName;
	private String roadAddressName;
	private String zipCode;
	private String imageUrl;
	private String direction;
	private List<String> tags;
	private String subwayInfo;
	private String companyId;
	private String companyName;
	private String agentName;
	private String cortarName;
}
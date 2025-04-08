package org.silsagusi.joonggaemoa.domain.article.service.dto;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
	private Long id;
	private String cortarNo;
	private String articleNo;
	private String name;
	private String realEstateType;
	private String tradeType;
	private String price;
	private Integer rentPrice;
	private String confirmedAt;
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

	public static ArticleResponse of(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.cortarNo(article.getCortarNo())
			.articleNo(article.getArticleNo())
			.name(article.getName())
			.realEstateType(article.getRealEstateType())
			.tradeType(article.getTradeType())
			.price(article.getPrice())
			.rentPrice(article.getRentPrice())
			.confirmedAt(article.getConfirmedAt())
			.lotAddressName(article.getLotAddressName())
			.roadAddressName(article.getRoadAddressName())
			.zipCode(article.getZoneNo())
			.imageUrl(article.getImageUrl())
			.direction(article.getDirection())
			.tags(article.getTags())
			.subwayInfo(article.getSubwayInfo())
			.companyId(article.getCompanyId())
			.companyName(article.getCompanyName())
			.agentName(article.getAgentName())
			.cortarName(article.getRegion().getCortarName())
			.build();
	}
}
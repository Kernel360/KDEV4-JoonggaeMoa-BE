package org.silsagusi.api.article.application;

import java.util.List;

import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

	public List<RealEstateTypeSummaryResponse> toRealEstateTypeSummaryResponse(
		List<ArticleTypeRatioProjection> realEstateTypeRatioProjections, long count) {
		return realEstateTypeRatioProjections.stream()
			.map(it -> RealEstateTypeSummaryResponse.builder()
				.type(it.getType())
				.ratio((it.getCount() * 100.0) / count)
				.build())
			.toList();
	}

	public List<TradeTypeSummaryResponse> toTradeTypeSummaryResponse(
		List<ArticleTypeRatioProjection> tradeTypeRatioProjections, long count) {
		return tradeTypeRatioProjections.stream()
			.map(it -> TradeTypeSummaryResponse.builder()
				.type(it.getType())
				.ratio((it.getCount() * 100.0) / count)
				.build())
			.toList();
	}

	public ArticleResponse toArticleResponse(Article article) {
		return ArticleResponse.builder()
			.id(article.getId())
			.latitude(article.getLatitude())
			.longitude(article.getLongitude())
			.cortarNo(article.getCortarNo())
			.articleNo(article.getArticleNo())
			.name(article.getName())
			.realEstateType(article.getRealEstateType())
			.tradeType(article.getTradeType())
			.price(article.getPrice() + "")
			.rentPrice(article.getRentPrice())
			.lotAddressName(article.getLotAddressName())
			.roadAddressName(article.getRoadAddressName())
			.zipCode(article.getZoneNo())
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

	public RegionResponse toRegionResponse(Region region) {
		return RegionResponse.builder()
			.cortarNo(region.getCortarNo())
			.centerLat(region.getCenterLat())
			.centerLon(region.getCenterLon())
			.cortarName(region.getCortarName())
			.cortarType(region.getCortarType())
			.build();
	}
}

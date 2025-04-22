package org.silsagusi.api.article.application;

import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.stereotype.Component;

import java.util.List;

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

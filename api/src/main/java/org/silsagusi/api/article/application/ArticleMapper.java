package org.silsagusi.api.article.application;

import java.util.List;

import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
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
}

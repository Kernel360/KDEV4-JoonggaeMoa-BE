package org.silsagusi.api.article.application.dto;

import java.util.List;

import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TradeTypeSummaryResponse {

	private final List<TradeTypeSummary> values;

	@Builder
	public static class TradeTypeSummary {
		private String type;
		private Double ratio;
	}

	public TradeTypeSummaryResponse(List<ArticleTypeRatioProjection> tradeTypeRatioProjections, long count) {
		this.values = tradeTypeRatioProjections.stream()
			.map(it -> TradeTypeSummary.builder()
				.type(it.getType())
				.ratio((it.getCount() * 100.0) / count)
				.build())
			.toList();
	}
}
package org.silsagusi.api.article.application.dto;

import java.io.Serializable;
import java.util.List;

import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TradeTypeSummaryResponse implements Serializable {

	private final List<TradeTypeSummary> values;

	public TradeTypeSummaryResponse(List<ArticleTypeRatioProjection> tradeTypeRatioProjections, long count) {
		this.values = tradeTypeRatioProjections.stream()
			.map(it -> TradeTypeSummary.builder()
				.type(it.getType())
				.ratio((it.getCount() * 100.0) / count)
				.build())
			.toList();
	}

	@Getter
	@Builder
	public static class TradeTypeSummary implements Serializable {
		private String type;
		private Double ratio;
	}
}
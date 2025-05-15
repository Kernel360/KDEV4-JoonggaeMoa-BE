package org.silsagusi.api.article.application.dto;

import java.io.Serializable;
import java.util.List;

import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RealEstateTypeSummaryResponse implements Serializable {

	private final List<RealEstateTypeSummary> values;

	public RealEstateTypeSummaryResponse(List<ArticleTypeRatioProjection> realEstateTypeRatioProjections, long count) {
		this.values = realEstateTypeRatioProjections.stream()
			.map(it -> RealEstateTypeSummary.builder()
				.type(it.getType())
				.ratio((it.getCount() * 100.0) / count)
				.build())
			.toList();
	}

	@Getter
	@Builder
	public static class RealEstateTypeSummary implements Serializable {
		private String type;
		private Double ratio;
	}
}

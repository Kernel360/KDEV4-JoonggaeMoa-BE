package org.silsagusi.api.article.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;

import java.util.List;

@Getter
public class RealEstateTypeSummaryResponse {

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
	public static class RealEstateTypeSummary {
		private String type;
		private Double ratio;
	}
}

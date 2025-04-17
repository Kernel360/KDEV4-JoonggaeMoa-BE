package org.silsagusi.api.article.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RealEstateTypeSummaryResponse {

	private String type;
	private Double ratio;
}

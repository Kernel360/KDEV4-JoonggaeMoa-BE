package org.silsagusi.api.article.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.core.domain.article.Region;

@Getter
@Builder
public class RegionResponse {
	private String cortarNo;
	private Double centerLat;
	private Double centerLon;
	private String cortarName;
	private String cortarType;

	public static RegionResponse toResponse(Region region) {
		return RegionResponse.builder()
			.cortarNo(region.getCortarNo())
			.centerLat(region.getCenterLat())
			.centerLon(region.getCenterLon())
			.cortarName(region.getCortarName())
			.cortarType(region.getCortarType())
			.build();
	}
}

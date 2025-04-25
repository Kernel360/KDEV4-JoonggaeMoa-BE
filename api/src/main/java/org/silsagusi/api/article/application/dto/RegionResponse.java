package org.silsagusi.api.article.application.dto;

import org.silsagusi.core.domain.article.Region;

import lombok.Builder;
import lombok.Getter;

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

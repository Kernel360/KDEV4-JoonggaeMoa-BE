package org.silsagusi.api.article.service.dto;

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

	public static RegionResponse of(Region region) {
		return RegionResponse.builder()
			.cortarNo(region.getCortarNo())
			.centerLat(region.getCenterLat())
			.centerLon(region.getCenterLon())
			.cortarName(region.getCortarName())
			.cortarType(region.getCortarType())
			.build();
	}
}

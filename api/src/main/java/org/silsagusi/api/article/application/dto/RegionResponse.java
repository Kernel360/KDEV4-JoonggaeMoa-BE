package org.silsagusi.api.article.application.dto;

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
}

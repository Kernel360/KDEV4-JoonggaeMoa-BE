package org.silsagusi.api.article.application.dto;

import org.silsagusi.api.article.infrastructure.dto.BoundingBoxInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoundingBoxRequest {
	private Double swLat;
	private Double neLat;
	private Double swLng;
	private Double neLng;

	public BoundingBoxInfo toInfo() {
		return new BoundingBoxInfo(swLat, neLat, swLng, neLng);
	}
}

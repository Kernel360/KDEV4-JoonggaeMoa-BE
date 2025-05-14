package org.silsagusi.api.article.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoundingBoxInfo {
	private Double swLat;
	private Double neLat;
	private Double swLng;
	private Double neLng;
}

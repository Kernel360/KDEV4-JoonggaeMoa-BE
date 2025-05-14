package org.silsagusi.api.article.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoundingBox {
	private Double swLat;
	private Double neLat;
	private Double swLng;
	private Double neLng;
}

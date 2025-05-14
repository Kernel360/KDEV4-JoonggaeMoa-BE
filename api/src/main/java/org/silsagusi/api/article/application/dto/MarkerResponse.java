package org.silsagusi.api.article.application.dto;

import java.util.List;

import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkerResponse {
	private Long id;
	@JsonRawValue
	private String geoJson;

	public static List<MarkerResponse> toResponses(List<MarkerProjection> projections) {
		return projections.stream()
			.map(projection -> new MarkerResponse(projection.getId(), projection.getGeoJson()))
			.toList();
	}
}

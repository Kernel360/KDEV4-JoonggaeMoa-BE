package org.silsagusi.api.article.application.dto;

import java.util.List;

import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;

import com.fasterxml.jackson.annotation.JsonRawValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClusterResponse {
	@JsonRawValue
	private String geoJson;
	private Long count;

	public static List<ClusterResponse> toResponses(List<ClusterProjection> projections) {
		return projections.stream()
			.map(projection -> new ClusterResponse(projection.getGeoJson(), projection.getCount()))
			.toList();
	}
}

package org.silsagusi.api.article.application.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;

@Getter
@AllArgsConstructor
public class ClusterResponse {
	@JsonRawValue
	private String geoJson;
	private Long count;

	public static ClusterResponse toResponse(ClusterProjection p) {
		return new ClusterResponse(
			p.getGeoJson(),
			p.getCount()
		);
	}
}

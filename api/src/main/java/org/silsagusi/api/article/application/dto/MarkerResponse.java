package org.silsagusi.api.article.application.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;

@Getter
@AllArgsConstructor
public class MarkerResponse {
	private Long id;
	@JsonRawValue
	private String geoJson;

	public static MarkerResponse toResponse(MarkerProjection p) {
		return new MarkerResponse(
			p.getId(),
			p.getGeoJson()
		);
	}
}

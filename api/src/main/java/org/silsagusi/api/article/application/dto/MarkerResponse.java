package org.silsagusi.api.article.application.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarkerResponse {
	private Long id;
	@JsonRawValue
	private String geoJson;
}

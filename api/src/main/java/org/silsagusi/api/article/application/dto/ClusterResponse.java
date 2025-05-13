package org.silsagusi.api.article.application.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClusterResponse {
	@JsonRawValue
	private String geoJson;
	private Long count;
}

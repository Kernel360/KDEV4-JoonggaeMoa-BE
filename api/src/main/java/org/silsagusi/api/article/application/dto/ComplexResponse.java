package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexResponse {
	private Long id;
	private String complexName;
	private String buildingType;
	private LocalDate confirmedAt;
}

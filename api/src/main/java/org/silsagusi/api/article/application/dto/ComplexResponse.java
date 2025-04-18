package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplexResponse {
	private Long id;
	private String name;
	private String type;
	private String approvedAt;
}

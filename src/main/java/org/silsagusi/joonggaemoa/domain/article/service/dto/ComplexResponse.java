package org.silsagusi.joonggaemoa.domain.article.service.dto;

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

	public static ComplexResponse of(ComplexResponse complexResponse) {
		return ComplexResponse.builder()
			.id(complexResponse.getId())
			.name(complexResponse.getName())
			.type(complexResponse.getType())
			.approvedAt(complexResponse.getApprovedAt())
			.build();
	}

}

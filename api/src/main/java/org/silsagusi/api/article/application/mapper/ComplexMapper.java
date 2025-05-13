package org.silsagusi.api.article.application.mapper;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ComplexResponse;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplexMapper {
	public ComplexResponse toResponse(Complex complex) {
		return ComplexResponse.builder()
			.id(complex.getId())
			.complexName(complex.getComplexName())
			.confirmedAt(complex.getConfirmedAt())
			.build();
	}
}

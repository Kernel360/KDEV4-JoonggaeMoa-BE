package org.silsagusi.batch.application;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplexValidator {

	private final ComplexRepository complexRepository;

	public boolean validateExist(Complex complex) {
		return complexRepository.findFirstByComplexCode(complex.getComplexCode()).isEmpty();
	}
}

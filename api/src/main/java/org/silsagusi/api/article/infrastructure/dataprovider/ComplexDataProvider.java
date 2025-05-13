package org.silsagusi.api.article.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ComplexRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ComplexDataProvider {
	private final ComplexRepository complexRepository;

	public List<Complex> getComplexes() {
		return complexRepository.findAll();
	}

	public Complex getComplexById(Long id) {
		return complexRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMPLEX));
	}
}

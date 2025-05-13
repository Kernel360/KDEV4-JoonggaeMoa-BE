package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ComplexResponse;
import org.silsagusi.api.article.application.mapper.ComplexMapper;
import org.silsagusi.api.article.infrastructure.dataprovider.ComplexDataProvider;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexDataProvider complexDataProvider;
	private final ComplexMapper complexMapper;

	@Transactional(readOnly = true)
	public List<Complex> getComplexes() {
		return complexDataProvider.getComplexes();
	}

	@Transactional(readOnly = true)
	public ComplexResponse getComplexById(Long id) {
		Complex complex = complexDataProvider.getComplexById(id);
		return complexMapper.toResponse(complex);
	}
}

package org.silsagusi.api.article.application.service;

import java.util.List;

import org.silsagusi.api.article.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexDataProvider complexDataProvider;

	@Transactional(readOnly = true)
	public List<Complex> getComplexes() {
		return complexDataProvider.getComplexes();
	}
}

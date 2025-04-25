package org.silsagusi.api.article.application;

import java.util.List;

import org.silsagusi.api.article.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexDataProvider complexDataProvider;

	public List<Complex> getComplexes() {
		return complexDataProvider.getComplexes();
	}
}

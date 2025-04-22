package org.silsagusi.api.article.application;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.dataProvider.ComplexDataProvider;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexDataProvider complexDataProvider;

	public List<Complex> getComplexes() {
		return complexDataProvider.getComplexes();
	}
}

package org.silsagusi.api.article.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ComplexRepository;
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
}

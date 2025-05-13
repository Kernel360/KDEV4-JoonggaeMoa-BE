package org.silsagusi.batch.infrastructure.dataprovider;

import java.util.List;
import java.util.NoSuchElementException;

import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComplexDataProvider {

	private final ComplexRepository complexRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveComplexes(List<Complex> complexes) {
		complexRepository.saveAll(complexes);
	}

	public Complex getComplexByComplexCode(String complexCode) {
		return complexRepository.findFirstByComplexCode(complexCode)
			.orElseThrow(NoSuchElementException::new);
	}
}

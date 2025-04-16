package org.silsagusi.api.article.service;

import java.util.List;

import org.silsagusi.api.article.infrastructure.ComplexRepository;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexRepository complexRepository;

	public List<Complex> getComplex() {
		return complexRepository.findAll();
	}
}

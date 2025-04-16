package org.silsagusi.joonggaemoa.api.article.service;

import lombok.RequiredArgsConstructor;

import org.silsagusi.joonggaemoa.core.domain.article.Complex;
import org.silsagusi.joonggaemoa.core.domain.article.infrastructure.ComplexRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexService {

	private final ComplexRepository complexRepository;

	public List<Complex> getComplex() {
		return complexRepository.findAll();
	}
}

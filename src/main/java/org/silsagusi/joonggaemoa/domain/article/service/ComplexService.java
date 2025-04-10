package org.silsagusi.joonggaemoa.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.article.entity.Complex;
import org.silsagusi.joonggaemoa.domain.article.repository.ComplexRepository;
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

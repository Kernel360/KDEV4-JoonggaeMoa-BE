package org.silsagusi.joonggaemoa.api.article.service;

import lombok.RequiredArgsConstructor;

import org.silsagusi.joonggaemoa.core.domain.article.infrastructure.RegionRepository;
import org.silsagusi.joonggaemoa.api.article.service.dto.RegionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionRepository regionRepository;

	public List<RegionResponse> getAllRegions() {
		return regionRepository.findAll().stream()
			.map(RegionResponse::of)
			.toList();
	}
}

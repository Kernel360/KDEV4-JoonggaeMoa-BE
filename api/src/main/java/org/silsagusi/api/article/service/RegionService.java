package org.silsagusi.api.article.service;

import java.util.List;

import org.silsagusi.api.article.infrastructure.RegionRepository;
import org.silsagusi.api.article.service.dto.RegionResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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

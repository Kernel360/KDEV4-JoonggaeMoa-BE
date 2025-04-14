package org.silsagusi.joonggaemoa.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.domain.article.service.dto.RegionResponse;
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

	public List<RegionResponse> getRegionsByCortarNo(String cortarNo) {
		return regionRepository.findAllByCortarNoIn(List.of(cortarNo)).stream()
				.map(RegionResponse::of)
				.toList();
	}
}

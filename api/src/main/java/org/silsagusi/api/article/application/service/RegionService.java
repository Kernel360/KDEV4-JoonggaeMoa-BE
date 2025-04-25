package org.silsagusi.api.article.application.service;

import java.util.List;

import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.api.article.infrastructure.dataProvider.RegionDataProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionDataProvider regionDataProvider;

	@Transactional(readOnly = true)
	public List<RegionResponse> getRegions() {
		return regionDataProvider.getRegions().stream()
			.map(RegionResponse::toResponse)
			.toList();
	}
}

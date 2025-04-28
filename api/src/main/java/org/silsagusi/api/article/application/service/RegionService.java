package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.api.article.infrastructure.dataProvider.RegionDataProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionDataProvider regionDataProvider;

	@Transactional(readOnly = true)
	public List<RegionResponse> getChildRegions(Long regionId) {
		var parent = regionDataProvider.getRegionById(regionId);
		if (parent == null || parent.getCortarNo() == null) {
			return List.of();
		}
		String prefix = parent.getCortarNo();
		return regionDataProvider.getChildRegionsByPrefix(prefix).stream()
			.map(RegionResponse::toResponse)
			.toList();
	}
}

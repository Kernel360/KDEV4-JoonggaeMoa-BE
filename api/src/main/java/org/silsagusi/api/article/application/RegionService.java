package org.silsagusi.api.article.application;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.api.article.infrastructure.dataProvider.RegionDataProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

	private final RegionDataProvider regionDataProvider;
	private final ArticleMapper articleMapper;

	public List<RegionResponse> getRegions() {
		return regionDataProvider.getRegions().stream()
			.map(articleMapper::toRegionResponse)
			.toList();
	}
}

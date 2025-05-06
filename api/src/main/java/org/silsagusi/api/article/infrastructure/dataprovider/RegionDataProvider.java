package org.silsagusi.api.article.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.RegionRepository;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RegionDataProvider {
	private final RegionRepository regionRepository;


	public List<Region> getChildRegionsByPrefix(String prefix) {
		return regionRepository.findAllByCortarNoStartingWith(prefix);
	}

}

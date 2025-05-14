package org.silsagusi.api.article.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.dto.BoundingBoxInfo;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.silsagusi.api.article.infrastructure.repository.MarkerRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MarkerDataProvider {

	private final MarkerRepository markerRepository;

	public List<MarkerProjection> getMarkers(BoundingBoxInfo box) {
		return markerRepository.findMarkers(box);
	}

	public List<MarkerProjection> getFilteredMarkers(
		BoundingBoxInfo box,
		String tradeType, String buildingTypeCode,
		Long minSalePrice, Long maxSalePrice,
		Long minRentPrice, Long maxRentPrice
	) {
		return markerRepository.findFilteredMarkers(
			box,
			List.of(tradeType), List.of(buildingTypeCode),
			minSalePrice, maxSalePrice,
			minRentPrice, maxRentPrice
		);
	}
}

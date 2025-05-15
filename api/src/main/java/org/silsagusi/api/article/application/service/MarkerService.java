package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.BoundingBoxRequest;
import org.silsagusi.api.article.application.dto.MarkerFilterRequest;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.validator.BoundingBoxValidator;
import org.silsagusi.api.article.application.validator.MarkerFilterValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.MarkerDataProvider;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkerService {
	private final MarkerDataProvider markerDataProvider;
	private final BoundingBoxValidator boundingBoxValidator;
	private final MarkerFilterValidator markerFilterValidator;

	@Transactional(readOnly = true)
	public List<MarkerResponse> getMarkers(BoundingBoxRequest boundingBoxRequest) {
		boundingBoxValidator.validateBoundingBox(boundingBoxRequest);
		List<MarkerProjection> projections = markerDataProvider.getMarkers(boundingBoxRequest.toInfo());
		return MarkerResponse.toResponses(projections);
	}

	@Transactional(readOnly = true)
	public List<MarkerResponse> getFilteredMarkers(
		BoundingBoxRequest boundingBoxRequest,
		MarkerFilterRequest markerFilterRequest
	) {
		boundingBoxValidator.validateBoundingBox(boundingBoxRequest);
		markerFilterValidator.validateFilter(markerFilterRequest);
		List<MarkerProjection> projections = markerDataProvider.getFilteredMarkers(
			boundingBoxRequest.toInfo(),
			markerFilterRequest.getTradeType(),
			markerFilterRequest.getBuildingTypeCode(),
			markerFilterRequest.getMinSalePrice(),
			markerFilterRequest.getMaxSalePrice(),
			markerFilterRequest.getMinRentPrice(),
			markerFilterRequest.getMaxRentPrice()
		);
		return MarkerResponse.toResponses(projections);
	}
}

package org.silsagusi.api.article.application.service;

import java.util.List;

import org.silsagusi.api.article.application.dto.BoundingBoxRequest;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.validator.BoundingBoxValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.ClusterDataProvider;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClusterService {

	private final ClusterDataProvider clusterDataProvider;
	private final BoundingBoxValidator boundingBoxValidator;

	@Transactional(readOnly = true)
	public List<MarkerResponse> getMarkers(BoundingBoxRequest boundingBoxRequest) {
		boundingBoxValidator.validateBoundingBox(boundingBoxRequest);
		List<MarkerProjection> projections = clusterDataProvider.getMarkers(boundingBoxRequest.toInfo());
		return MarkerResponse.toResponses(projections);
	}

	@Transactional(readOnly = true)
	public List<ClusterResponse> getClusters(BoundingBoxRequest boundingBoxRequest, Integer zoomLevel) {
		boundingBoxValidator.validateBoundingBox(boundingBoxRequest);
		List<ClusterProjection> projections = clusterDataProvider.getClusters(boundingBoxRequest.toInfo(), zoomLevel);
		return ClusterResponse.toResponses(projections);
	}
}

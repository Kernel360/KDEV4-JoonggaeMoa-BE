package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.BoundingBoxRequest;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.validator.BoundingBoxValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.ClusterDataProvider;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClusterService {

	private final ClusterDataProvider clusterDataProvider;
	private final BoundingBoxValidator boundingBoxValidator;

	@Transactional(readOnly = true)
	public List<ClusterResponse> getClusters(BoundingBoxRequest boundingBoxRequest, Integer zoomLevel) {
		boundingBoxValidator.validateBoundingBox(boundingBoxRequest);
		List<ClusterProjection> projections = clusterDataProvider.getClusters(boundingBoxRequest.toInfo(), zoomLevel);
		return ClusterResponse.toResponses(projections);
	}
}

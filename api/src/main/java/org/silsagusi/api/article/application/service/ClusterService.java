package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.mapper.ClusterMapper;
import org.silsagusi.api.article.application.validator.BoundingBoxValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.ClusterDataProvider;
import org.silsagusi.api.article.infrastructure.dto.BoundingBox;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class ClusterService {

	private static final NavigableMap<Integer, Integer> ZOOM_TO_PRECISION = new TreeMap<>(Map.of(
		0, 2, 5, 4, 8, 6, 12, 8
	));
	private final ClusterDataProvider clusterDataProvider;
	private final BoundingBoxValidator boundingBoxValidator;
	private final ClusterMapper clusterMapper;

	@Transactional(readOnly = true)
	public List<MarkerResponse> getMarkers(BoundingBox box) {
		boundingBoxValidator.validateBoundingBox(box);
		List<MarkerProjection> projections = clusterDataProvider.getMarkers(box);
		return clusterMapper.toMarkerResponseList(projections);
	}

	@Transactional(readOnly = true)
	public List<ClusterResponse> getClusters(BoundingBox box, Integer zoomLevel) {
		boundingBoxValidator.validateBoundingBox(box);
		int precision = ZOOM_TO_PRECISION.floorEntry(zoomLevel).getValue();
		List<ClusterProjection> projections = clusterDataProvider.getClusters(box, precision);
		return clusterMapper.toClusterResponseList(projections);
	}
}

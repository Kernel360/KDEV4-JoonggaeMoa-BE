package org.silsagusi.api.article.application.mapper;

import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;

import java.util.List;
import java.util.stream.Collectors;

public class ClusterMapper {

	public static List<MarkerResponse> toMarkerResponseList(List<MarkerProjection> projections) {
		return projections.stream()
			.map(ClusterMapper::toMarkerResponse)
			.collect(Collectors.toList());
	}

	public static MarkerResponse toMarkerResponse(MarkerProjection p) {
		return new MarkerResponse(
			p.getId(),
			p.getGeoJson()
		);
	}

	public static List<ClusterResponse> toClusterResponseList(List<ClusterProjection> projections) {
		return projections.stream()
			.map(ClusterMapper::toClusterResponse)
			.collect(Collectors.toList());
	}

	public static ClusterResponse toClusterResponse(ClusterProjection p) {
		return new ClusterResponse(
			p.getGeoJson(),
			p.getCount()
		);
	}
}

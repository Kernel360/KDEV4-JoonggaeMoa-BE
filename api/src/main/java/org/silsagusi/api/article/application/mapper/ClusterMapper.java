package org.silsagusi.api.article.application.mapper;

import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClusterMapper {

	public List<MarkerResponse> toMarkerResponseList(List<MarkerProjection> projections) {
		return projections.stream()
			.map(MarkerResponse::toResponse)
			.collect(Collectors.toList());
	}

	public List<ClusterResponse> toClusterResponseList(List<ClusterProjection> projections) {
		return projections.stream()
			.map(ClusterResponse::toResponse)
			.collect(Collectors.toList());
	}
}

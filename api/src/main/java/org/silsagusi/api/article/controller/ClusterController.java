package org.silsagusi.api.article.controller;

import java.util.List;

import org.silsagusi.api.article.application.dto.BoundingBoxRequest;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.service.ClusterService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ClusterController {

	private final ClusterService clusterService;

	@GetMapping("/api/article/markers")
	public ApiResponse<List<MarkerResponse>> getMarkers(
		@ModelAttribute BoundingBoxRequest boundingBoxRequest
	) {
		return ApiResponse.ok(clusterService.getMarkers(boundingBoxRequest));
	}

	@GetMapping("/api/article/clusters")
	public ApiResponse<List<ClusterResponse>> getClusters(
		@ModelAttribute BoundingBoxRequest boundingBoxRequest,
		@RequestParam Integer zoomLevel
	) {
		return ApiResponse.ok(clusterService.getClusters(boundingBoxRequest, zoomLevel));
	}
}

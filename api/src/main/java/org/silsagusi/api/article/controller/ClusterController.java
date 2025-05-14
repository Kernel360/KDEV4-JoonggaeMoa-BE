package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.service.ClusterService;
import org.silsagusi.api.article.infrastructure.dto.BoundingBox;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClusterController {

	private final ClusterService clusterService;

	@GetMapping("/api/article/markers")
	public ApiResponse<List<MarkerResponse>> markers(
		@RequestParam Double swLat,
		@RequestParam Double neLat,
		@RequestParam Double swLng,
		@RequestParam Double neLng
	) {
		BoundingBox box = new BoundingBox(swLat, neLat, swLng, neLng);
		return ApiResponse.ok(clusterService.getMarkers(box));
	}

	@GetMapping("/api/article/clusters")
	public ApiResponse<List<ClusterResponse>> clusters(
		@RequestParam Double swLat,
		@RequestParam Double neLat,
		@RequestParam Double swLng,
		@RequestParam Double neLng,
		@RequestParam Integer zoomLevel
	) {
		BoundingBox box = new BoundingBox(swLat, neLat, swLng, neLng);
		return ApiResponse.ok(clusterService.getClusters(box, zoomLevel));
	}
}

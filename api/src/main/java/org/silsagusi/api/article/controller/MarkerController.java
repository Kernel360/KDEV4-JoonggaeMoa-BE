package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.BoundingBoxRequest;
import org.silsagusi.api.article.application.dto.MarkerFilterRequest;
import org.silsagusi.api.article.application.dto.MarkerResponse;
import org.silsagusi.api.article.application.service.MarkerService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MarkerController {
	private final MarkerService markerService;

	@GetMapping("/api/article/markers")
	public ApiResponse<List<MarkerResponse>> getMarkers(
		@ModelAttribute BoundingBoxRequest boundingBoxRequest
	) {
		return ApiResponse.ok(markerService.getMarkers(boundingBoxRequest));
	}

	@GetMapping("/api/article/markers/filter")
	public ApiResponse<List<MarkerResponse>> getFilteredMarkers(
		@ModelAttribute BoundingBoxRequest boundingBoxRequest,
		@ModelAttribute MarkerFilterRequest markerFilterRequest
	) {
		return ApiResponse.ok(markerService.getFilteredMarkers(boundingBoxRequest, markerFilterRequest));
	}
}

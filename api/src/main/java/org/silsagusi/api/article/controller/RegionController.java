package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.RegionService;
import org.silsagusi.api.article.application.dto.RegionResponse;
import org.silsagusi.core.customResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@GetMapping("/api/regions")
	public ResponseEntity<ApiResponse<List<RegionResponse>>> getRegions() {

		return ResponseEntity.ok(ApiResponse.ok(
			regionService.getRegions()
		));
	}
}

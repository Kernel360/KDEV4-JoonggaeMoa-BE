package org.silsagusi.joonggaemoa.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.article.service.RegionService;
import org.silsagusi.joonggaemoa.domain.article.service.dto.RegionResponse;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RegionController {

	private final RegionService regionService;

	@GetMapping("/api/regions")
	public ResponseEntity<ApiResponse<List<RegionResponse>>> getRegions() {
		return ResponseEntity.ok(ApiResponse.ok(
			regionService.getAllRegions()
		));
	}

	@GetMapping("/api/regions/{cortarNo}")
	public ResponseEntity<ApiResponse<List<RegionResponse>>> getRegionsByCortarNo(
		@PathVariable String cortarNo
	) {
	    return ResponseEntity.ok(ApiResponse.ok(
	        regionService.getRegionsByCortarNo(cortarNo)
	    ));
	}
}

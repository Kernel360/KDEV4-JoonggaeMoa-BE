package org.silsagusi.api.article.controller;

import java.util.List;

import org.silsagusi.api.article.application.ComplexService;
import org.silsagusi.api.article.application.dto.ComplexResponse;
import org.silsagusi.core.customResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ComplexController {

	private final ComplexService complexService;

	@GetMapping("/api/naverbudongsan/complexes")
	public ResponseEntity<ApiResponse<List<ComplexResponse>>> getComplexes() {
		List<ComplexResponse> complexResponseList = complexService.getComplexes().stream()
			.map(complex -> new ComplexResponse()).toList();
		return ResponseEntity.ok(ApiResponse.ok(complexResponseList));
	}
}

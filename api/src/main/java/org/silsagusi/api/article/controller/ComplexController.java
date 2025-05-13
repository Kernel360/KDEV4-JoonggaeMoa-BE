package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ComplexResponse;
import org.silsagusi.api.article.application.service.ComplexService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ComplexController {

	private final ComplexService complexService;

	@GetMapping("/api/complexes")
	public ResponseEntity<ApiResponse<List<ComplexResponse>>> getComplexes() {
		List<ComplexResponse> complexResponseList = complexService.getComplexes().stream()
			.map(complex -> new ComplexResponse()).toList();
		return ResponseEntity.ok(ApiResponse.ok(complexResponseList));
	}

	@GetMapping("/api/complexes/{id}")
	public ResponseEntity<ApiResponse<ComplexResponse>> getComplexById(
		@PathVariable Long id
	) {
		ComplexResponse complexResponse = complexService.getComplexById(id);
		return ResponseEntity.ok(ApiResponse.ok(complexResponse));
	}
}

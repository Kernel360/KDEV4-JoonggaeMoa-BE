package org.silsagusi.joonggaemoa.domain.article.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.service.ComplexService;
import org.silsagusi.joonggaemoa.domain.article.service.dto.ComplexResponse;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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
		List<ComplexResponse> complexResponseList = complexService.getComplex().stream()
			.map(complex -> new ComplexResponse()).toList();
		return ResponseEntity.ok(ApiResponse.ok(complexResponseList));
	}
}

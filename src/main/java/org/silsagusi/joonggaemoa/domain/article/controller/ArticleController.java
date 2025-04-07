package org.silsagusi.joonggaemoa.domain.article.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.service.ArticleService;
import org.silsagusi.joonggaemoa.domain.article.service.dto.ArticleResponse;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/api/articles")
	public ResponseEntity<ApiResponse<Page<ArticleResponse>>> getArticles(
		Pageable pageable,
		@RequestParam(required = false) List<String> realEstateType,
		@RequestParam(required = false) List<String> tradeType,
		@RequestParam(required = false) Long minPrice,
		@RequestParam(required = false) Long maxPrice
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getAllArticles(pageable, realEstateType, tradeType, minPrice, maxPrice)
		));
	}
}
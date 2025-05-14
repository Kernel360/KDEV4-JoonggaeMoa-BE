package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/api/article/{articleId}")
	public ResponseEntity<ApiResponse<ArticleResponse>> getArticleInfo(
		@PathVariable Long articleId
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getArticleInfo(articleId)
		));
	}

	@GetMapping("/api/dashboard/real-estate-type-summary")
	public ResponseEntity<ApiResponse<RealEstateTypeSummaryResponse>> getRealEstateTypeSummary(
		@RequestParam String period
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getRealEstateTypeSummary(period)
		));
	}

	@GetMapping("/api/dashboard/trade-type-summary")
	public ResponseEntity<ApiResponse<TradeTypeSummaryResponse>> getTradeTypeSummary(
		@RequestParam String period
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getTradeTypeSummary(period)
		));
	}
}
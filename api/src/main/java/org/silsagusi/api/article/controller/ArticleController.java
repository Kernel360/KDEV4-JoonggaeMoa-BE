package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/api/articles")
	public ResponseEntity<ApiResponse<Page<ArticleResponse>>> getArticles(
		Pageable pageable,
		@RequestParam(required = false) List<String> realEstateType,
		@RequestParam(required = false) List<String> tradeType,
		@RequestParam(required = false) String minPrice,
		@RequestParam(required = false) String maxPrice
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getAllArticles(
				pageable, realEstateType, tradeType, minPrice, maxPrice
			)
		));
	}

	@GetMapping("/api/dashboard/real-estate-type-summary")
	public ResponseEntity<ApiResponse<List<RealEstateTypeSummaryResponse>>> getRealEstateTypeSummary(
		@RequestParam String period                            // period = [daily, weekly, monthly]
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getRealEstateTypeSummary(period)
		));
	}

	@GetMapping("/api/dashboard/trade-type-summary")
	public ResponseEntity<ApiResponse<List<TradeTypeSummaryResponse>>> getTradeTypeSummary(
		@RequestParam String period
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			articleService.getTradeTypeSummary(period)
		));
	}
}
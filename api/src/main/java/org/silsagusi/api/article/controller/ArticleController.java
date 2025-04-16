package org.silsagusi.api.article.controller;

import java.util.List;

import org.silsagusi.api.article.service.ArticleService;
import org.silsagusi.api.article.service.RegionService;
import org.silsagusi.api.article.service.dto.ArticleResponse;
import org.silsagusi.api.article.service.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.service.dto.RegionResponse;
import org.silsagusi.api.article.service.dto.TradeTypeSummaryResponse;
import org.silsagusi.core.customResponse.ApiResponse;
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
	private final RegionService regionService;

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

	@GetMapping("/api/regions")
	public ResponseEntity<ApiResponse<List<RegionResponse>>> getRegions() {
		return ResponseEntity.ok(ApiResponse.ok(
			regionService.getAllRegions()
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
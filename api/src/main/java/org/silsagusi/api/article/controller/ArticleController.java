package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.*;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.response.ApiResponse;
import org.silsagusi.core.domain.article.Cluster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/api/articles")
	public PagedModel<EntityModel<ArticleResponse>> getArticles(
		@PageableDefault(sort = "priceSale", direction = Direction.DESC, size = 500) Pageable pageable,
		PagedResourcesAssembler<ArticleResponse> assembler,
		@RequestParam(required = false) List<String> realEstateType,
		@RequestParam(required = false) List<String> tradeType,
		@RequestParam(required = false) String minPrice,
		@RequestParam(required = false) String maxPrice,
		@RequestParam(required = false) String regionPrefix,
		@RequestParam(required = false) Double neLat,
		@RequestParam(required = false) Double neLng,
		@RequestParam(required = false) Double swLat,
		@RequestParam(required = false) Double swLng
	) {
		Page<ArticleResponse> page = articleService.getAllArticles(
			pageable, realEstateType, tradeType, minPrice, maxPrice,
			regionPrefix, neLat, neLng, swLat, swLng
		);
		return assembler.toModel(page);
	}

	@GetMapping("/api/articles/{id}")
	public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(
		@PathVariable Long id
	) {
		ArticleResponse articleResponse = articleService.getArticleById(id);
		return ResponseEntity.ok(ApiResponse.ok(articleResponse));
	}

	@GetMapping("/api/articles/search")
	public ResponseEntity<ApiResponse<ArticleListResponse>> searchArticles(
		@RequestParam(required = false) List<String> realEstateType,
		@RequestParam(required = false) List<String> tradeType,
		@RequestParam(required = false) String minPrice,
		@RequestParam(required = false) String maxPrice,
		@RequestParam(required = false) String regionPrefix,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "confirmedAt") String sortBy,
		@RequestParam(defaultValue = "desc")     String direction
	) {
		ArticleListResponse result = articleService.searchArticles(
			realEstateType, tradeType, minPrice, maxPrice,
			regionPrefix, page, size, sortBy, direction
		);
		return ResponseEntity.ok(ApiResponse.ok(result));
	}

	@GetMapping("/api/clusters")
	public ResponseEntity<ApiResponse<List<Cluster>>> getClusters(
		@RequestParam double swLat,
		@RequestParam double neLat,
		@RequestParam double swLng,
		@RequestParam double neLng,
		@RequestParam(defaultValue = "6") int precision
	) {
		List<Cluster> list = articleService.getClusters(swLat, neLat, swLng, neLng, precision);
		return ResponseEntity.ok(ApiResponse.ok(list));
	}

	@GetMapping("/api/articles/by-cluster")
	public ResponseEntity<ApiResponse<List<ArticleResponse>>> getByCluster(
		@RequestParam String clusterId,
		@RequestParam int    precision,
		@RequestParam(defaultValue = "0")   int page,
		@RequestParam(defaultValue = "100") int size
	) {
		List<ArticleResponse> list = articleService.getArticlesByCluster(
			clusterId, precision, page, size
		);
		return ResponseEntity.ok(ApiResponse.ok(list));
	}

	@GetMapping("/api/dashboard/real-estate-type-summary")
	public ResponseEntity<ApiResponse<RealEstateTypeSummaryResponse>> getRealEstateTypeSummary(
		@RequestParam String period                            // period = [daily, weekly, monthly]
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
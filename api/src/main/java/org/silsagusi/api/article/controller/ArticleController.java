package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.*;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.article.controller.request.ArticleSearchCriteria;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/api/articles")
	public PagedModel<EntityModel<ArticleResponse>> getArticles(
		@RequestParam(value = "type", defaultValue = "filter") String type,
		@ModelAttribute ArticleSearchCriteria criteria,
		@PageableDefault(sort = "priceSale", direction = Direction.DESC, size = 500) Pageable pageable,
		PagedResourcesAssembler<ArticleResponse> assembler
	) {
		Page<ArticleResponse> page = articleService.getAllArticles(
			type, pageable,
			criteria.getRealEstateType(),
			criteria.getTradeType(),
			criteria.getMinPrice(),
			criteria.getMaxPrice(),
			criteria.getRegionPrefix(),
			criteria.getNeLat(),
			criteria.getNeLng(),
			criteria.getSwLat(),
			criteria.getSwLng()
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
		@ModelAttribute ArticleSearchCriteria criteria,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size,
		@RequestParam(defaultValue = "confirmedAt") String sortBy,
		@RequestParam(defaultValue = "desc")     String direction
	) {
		ArticleListResponse result = articleService.searchArticles(
			criteria.getRealEstateType(),
			criteria.getTradeType(),
			criteria.getMinPrice(),
			criteria.getMaxPrice(),
			criteria.getRegionPrefix(),
			page, size, sortBy, direction
		);
		return ResponseEntity.ok(ApiResponse.ok(result));
	}

	@GetMapping("/api/clusters")
	public ResponseEntity<ApiResponse<List<ClusterResponse>>> getClusters(
		@RequestParam double swLat,
		@RequestParam double neLat,
		@RequestParam double swLng,
		@RequestParam double neLng,
		@RequestParam int zoomLevel
	) {
		List<ClusterResponse> list = articleService.getClusters(swLat, neLat, swLng, neLng, zoomLevel);
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
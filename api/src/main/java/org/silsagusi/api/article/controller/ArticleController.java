package org.silsagusi.api.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.ClusterInfo;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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

	@GetMapping("/api/articles/clusters")
	public List<ClusterInfo> getClusters(
		@RequestParam double swLat,
		@RequestParam double neLat,
		@RequestParam double swLng,
		@RequestParam double neLng,
		@RequestParam(defaultValue = "6") int precision
	) {
		return articleService.getClusters(swLat, neLat, swLng, neLng, precision);
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
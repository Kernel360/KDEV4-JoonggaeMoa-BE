package org.silsagusi.api.article.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.silsagusi.api.article.application.assembler.ArticleListModelAssembler;
import org.silsagusi.api.article.application.assembler.ArticleModelAssembler;
import org.silsagusi.api.article.application.assembler.ClusterModelAssembler;
import org.silsagusi.api.article.application.dto.ArticleListResponse;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.service.ArticleService;
import org.silsagusi.api.article.controller.request.ArticleListRequest;
import org.silsagusi.api.article.controller.request.ArticleSearchCriteria;
import org.silsagusi.api.article.controller.request.ClusterRequest;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping(produces = "application/hal+json")
@RestController
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleService articleService;
	private final ClusterModelAssembler clusterModelAssembler;
	private final ArticleListModelAssembler articleListModelAssembler;
	private final ArticleModelAssembler articleModelAssembler;

	@GetMapping("/api/articles")
	public PagedModel<EntityModel<ArticleResponse>> getArticles(
		@RequestParam(value = "type", defaultValue = "filter") String type,
		@ModelAttribute ArticleSearchCriteria criteria,
		@PageableDefault(sort = "priceSale", direction = Direction.DESC, size = 500) Pageable pageable,
		PagedResourcesAssembler<ArticleResponse> assembler
	) {
		Page<ArticleResponse> page = articleService.getArticles(
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

	@GetMapping(value = "/api/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<ArticleResponse>> getArticleById(
		@PathVariable Long id
	) {
		ArticleResponse articleResponse = articleService.getArticleById(id);
		return ResponseEntity.ok(ApiResponse.ok(articleResponse));
	}

	@GetMapping("/api/articles/search")
	public ResponseEntity<EntityModel<ArticleListResponse>> searchArticles(
		@ModelAttribute ArticleListRequest articleListRequest
		) {
		ArticleListResponse result = articleService.searchArticles(
			articleListRequest.getCriteria().getRealEstateType(),
			articleListRequest.getCriteria().getTradeType(),
			articleListRequest.getCriteria().getMinPrice(),
			articleListRequest.getCriteria().getMaxPrice(),
			articleListRequest.getCriteria().getRegionPrefix(),
			articleListRequest.getPage(),
			articleListRequest.getSize(),
			articleListRequest.getSortBy(),
			articleListRequest.getDirection().toString()
		);
		EntityModel<ArticleListResponse> model = articleListModelAssembler.toModel(result);
		return ResponseEntity.ok(model);
	}

	@GetMapping("/api/articles/by-cluster")
	public ResponseEntity<CollectionModel<EntityModel<ArticleResponse>>> getArticlesByCluster(
		@RequestParam String clusterId,
		@RequestParam int precision,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "100") int size
	) {
		List<ArticleResponse> list = articleService.getArticlesByCluster(
				clusterId, precision, page, size);
		List<EntityModel<ArticleResponse>> entityModels = list.stream()
				.map(articleModelAssembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModels));
	}

	@GetMapping("/api/clusters")
	public ResponseEntity<CollectionModel<EntityModel<ClusterRequest>>> getClusters(
		@ModelAttribute ClusterRequest clusterRequest
	) {
		List<ClusterRequest> list = articleService.getClusters(
			clusterRequest.getSwLat(),
			clusterRequest.getNeLat(),
			clusterRequest.getSwLng(),
			clusterRequest.getNeLng(),
			clusterRequest.getZoomLevel()
		);
		List<EntityModel<ClusterRequest>> entityModels = list.stream()
			.map(clusterModelAssembler::toModel)
			.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModels));
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
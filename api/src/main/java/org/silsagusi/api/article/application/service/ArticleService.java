package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.api.article.application.dto.ArticleListResponse;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.ClusterSummaryResponse;
import org.silsagusi.api.article.application.validator.ArticleValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.ArticleDataProvider;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleDataProvider articleDataProvider;
	private final ArticleValidator articleValidator;

	@Transactional(readOnly = true)
	public Page<ArticleResponse> getArticles(
		String type, Pageable pageable, List<String> realEstateType, List<String> tradeType,
		String minPrice, String maxPrice, String regionPrefix,
		Double neLat, Double neLng, Double swLat, Double swLng
	) {
		Page<Article> articlePage;
		articleValidator.validateSearchParams(
			realEstateType, tradeType, minPrice, maxPrice,
			regionPrefix, neLat, neLng, swLat, swLng);
		articleValidator.validateTypeParam(type);

		if ("bounds".equalsIgnoreCase(type)) {
			articleValidator.validateBoundsParams(swLat, neLat, swLng, neLng);
			articlePage = articleDataProvider.getArticlesByBounds(
				swLat, neLat, swLng, neLng, regionPrefix, pageable);

		} else if ("region".equalsIgnoreCase(type)) {
			articlePage = articleDataProvider.getArticlesByRegionPrefix(regionPrefix, pageable);

		} else {
			var spec = articleDataProvider.getArticleSpec(
				realEstateType, tradeType, minPrice, maxPrice);
			articlePage = articleDataProvider.getArticlePage(spec, pageable);
		}
		return articlePage.map(ArticleResponse::toResponse);
	}

	@Transactional(readOnly = true)
	public ArticleResponse getArticleById(Long id) {
		Article article = articleDataProvider.getArticleById(id);
		return ArticleResponse.toResponse(article);
	}

	@Transactional(readOnly = true)
	public ArticleListResponse searchArticles(
		List<String> realEstateType, List<String> tradeType,
		String minPrice, String maxPrice, String regionPrefix,
		int page, int size,
		String sortBy, String direction
	) {
		articleValidator.validateSearchParams(
			realEstateType, tradeType, minPrice, maxPrice,
			regionPrefix, null, null, null, null
		);
		Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Article> p = articleDataProvider.searchByParams(
			realEstateType, tradeType, minPrice, maxPrice,
			regionPrefix, pageable
		);
		List<ArticleResponse> list = p.getContent().stream()
			.map(ArticleResponse::toResponse)
			.collect(Collectors.toList());
		return new ArticleListResponse(
			list,
			p.getTotalElements(),
			p.getTotalPages(),
			p.getSize(),
			p.getNumber(),
			p.isLast()
		);
	}

	@Transactional(readOnly = true)
	public List<ArticleResponse> getArticlesByCluster(
		String clusterId, int precision, int page, int size
	) {
		List<Article> articles = articleDataProvider.getArticlesByCluster(
			clusterId, precision, page, size
		);
		return articles.stream()
			.map(ArticleResponse::toResponse)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ClusterSummaryResponse> getClustersByMarker(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	) {
		if (zoomLevel <= 4) {
			return articleDataProvider.getClustersByMarker(swLat, neLat, swLng, neLng, zoomLevel);
		} else if (zoomLevel <= 6) {
			return articleDataProvider.getClustersByBounds(swLat, neLat, swLng, neLng, zoomLevel);
		} else if (zoomLevel <= 8) {
			return articleDataProvider.getClustersByGu(swLat, neLat, swLng, neLng, zoomLevel);
		} else {
			return articleDataProvider.getClustersBySi(swLat, neLat, swLng, neLng, zoomLevel);
		}
	}

	@Transactional(readOnly = true)
	public RealEstateTypeSummaryResponse getRealEstateTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> realEstateTypeRatioProjections = articleDataProvider.getRealEstateTypeRatio(
			from);

		long realEstateTotalCount = articleDataProvider.sumArticleCount(realEstateTypeRatioProjections);

		return new RealEstateTypeSummaryResponse(realEstateTypeRatioProjections, realEstateTotalCount);
	}

	@Transactional(readOnly = true)
	public TradeTypeSummaryResponse getTradeTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> tradeTypeRatioProjections = articleDataProvider.getTradeTypeRatio(from);

		long tradeTotalCount = articleDataProvider.sumArticleCount(tradeTypeRatioProjections);

		return new TradeTypeSummaryResponse(tradeTypeRatioProjections, tradeTotalCount);
	}
}

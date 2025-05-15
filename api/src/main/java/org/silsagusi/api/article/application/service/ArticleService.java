package org.silsagusi.api.article.application.service;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.validator.ArticleValidator;
import org.silsagusi.api.article.infrastructure.dataprovider.ArticleDataProvider;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleDataProvider articleDataProvider;
	private final ArticleValidator articleValidator;

	@Transactional(readOnly = true)
	public ArticleResponse getArticleInfo(Long articleId) {
		articleValidator.validateArticleExists(articleId);
		Article article = articleDataProvider.getArticleInfo(articleId);
		return ArticleResponse.toResponse(article);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "realEstateTypeSummary", key = "#period + '_' + T(java.time.LocalDate).now().toString()")
	public RealEstateTypeSummaryResponse getRealEstateTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> realEstateTypeRatioProjections = articleDataProvider.getRealEstateTypeRatio(
			from);

		long realEstateTotalCount = articleDataProvider.sumArticleCount(realEstateTypeRatioProjections);

		return new RealEstateTypeSummaryResponse(realEstateTypeRatioProjections, realEstateTotalCount);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "tradeTypeSummary", key = "#period + '_' + T(java.time.LocalDate).now().toString()")
	public TradeTypeSummaryResponse getTradeTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> tradeTypeRatioProjections = articleDataProvider.getTradeTypeRatio(from);

		long tradeTotalCount = articleDataProvider.sumArticleCount(tradeTypeRatioProjections);

		return new TradeTypeSummaryResponse(tradeTypeRatioProjections, tradeTotalCount);
	}
}

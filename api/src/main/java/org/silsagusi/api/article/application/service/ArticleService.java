package org.silsagusi.api.article.application.service;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.application.mapper.ArticleMapper;
import org.silsagusi.api.article.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleDataProvider articleDataProvider;
	private final ArticleMapper articleMapper;

	@Transactional(readOnly = true)
	public Page<ArticleResponse> getAllArticles(
		Pageable pageable,
		List<String> realEstateType,
		List<String> tradeType,
		String minPrice,
		String maxPrice
	) {
		Specification<Article> spec = articleDataProvider.getArticleSpec(realEstateType, tradeType, minPrice, maxPrice);

		Page<Article> articlePage = articleDataProvider.getArticlePage(spec, pageable);

		return articlePage.map(articleMapper::toArticleResponse);
	}

	@Transactional(readOnly = true)
	public List<RealEstateTypeSummaryResponse> getRealEstateTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> realEstateTypeRatioProjections = articleDataProvider.getRealEstateTypeRatio(
			from);

		long realEstateTotalCount = articleDataProvider.sumArticleCount(realEstateTypeRatioProjections);

		return articleMapper.toRealEstateTypeSummaryResponse(realEstateTypeRatioProjections, realEstateTotalCount);
	}

	@Transactional(readOnly = true)
	public List<TradeTypeSummaryResponse> getTradeTypeSummary(String period) {
		LocalDate from = articleDataProvider.calculateStartDate(period);

		List<ArticleTypeRatioProjection> tradeTypeRatioProjections = articleDataProvider.getTradeTypeRatio(from);

		long tradeTotalCount = articleDataProvider.sumArticleCount(tradeTypeRatioProjections);

		return articleMapper.toTradeTypeSummaryResponse(tradeTypeRatioProjections, tradeTotalCount);
	}
}

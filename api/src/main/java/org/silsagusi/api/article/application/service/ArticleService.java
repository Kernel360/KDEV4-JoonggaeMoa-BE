package org.silsagusi.api.article.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.application.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.api.article.application.dto.TradeTypeSummaryResponse;
import org.silsagusi.api.article.infrastructure.dataProvider.ArticleDataProvider;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleDataProvider articleDataProvider;

	@Transactional(readOnly = true)
	public Page<ArticleResponse> getAllArticles(
	    Pageable pageable,
	    List<String> realEstateType, List<String> tradeType,
	    String minPrice, String maxPrice,
	    String regionPrefix,
	    Double neLat, Double neLng,
	    Double swLat, Double swLng
	) {
	    // 화면에 보이는 영역(bounds) 내 매물 조회
	    if (neLat != null && neLng != null && swLat != null && swLng != null) {
	        Page<Article> articlePage = articleDataProvider.getArticlesByBounds(
	            swLat, neLat, swLng, neLng, regionPrefix, pageable
	        );
	        return articlePage.map(ArticleResponse::toResponse);
	    }
	    if (regionPrefix != null && !regionPrefix.isEmpty()) {
	        Page<Article> articlePage = articleDataProvider.getArticlesByRegionPrefix(
	            regionPrefix, pageable
	        );
	        return articlePage.map(ArticleResponse::toResponse);
	    }
	    Specification<Article> spec = articleDataProvider.getArticleSpec(
	        realEstateType, tradeType, minPrice, maxPrice
	    );
	    Page<Article> articlePage = articleDataProvider.getArticlePage(
	        spec, pageable
	    );
	    return articlePage.map(ArticleResponse::toResponse);
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

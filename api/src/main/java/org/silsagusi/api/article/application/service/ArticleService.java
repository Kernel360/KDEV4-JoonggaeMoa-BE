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
        int page,
        int size,
        List<String> realEstateType,
        List<String> tradeType,
        String minPrice,
        String maxPrice,
        String sortBy,
        String direction,
        Long regionId
    ) {
        Specification<Article> spec = articleDataProvider.getArticleSpec(
            realEstateType, tradeType, minPrice, maxPrice
        );
        if (regionId != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("region").get("id"), regionId)
            );
        }
        Page<Article> articlePage = articleDataProvider.getArticlePage(
            spec, page, size, sortBy, direction
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

package org.silsagusi.joonggaemoa.api.article.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.api.article.entity.Article;
import org.silsagusi.joonggaemoa.api.article.entity.projection.RealEstateTypeRatioProjection;
import org.silsagusi.joonggaemoa.api.article.entity.projection.TradeTypeRatioProjection;
import org.silsagusi.joonggaemoa.api.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.api.article.service.dto.ArticleResponse;
import org.silsagusi.joonggaemoa.api.article.service.dto.RealEstateTypeSummaryResponse;
import org.silsagusi.joonggaemoa.api.article.service.dto.TradeTypeSummaryResponse;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {

    private static final String REAL_ESTATE_TYPE = "realEstateType";
    private static final String TRADE_TYPE = "tradeType";
    private static final String PRICE = "price";

    private final ArticleRepository articleRepository;

    public Page<ArticleResponse> getAllArticles(
        Pageable pageable,
        List<String> realEstateType,
        List<String> tradeType,
        String minPrice,
        String maxPrice
    ) {
        Specification<Article> spec = Specification.where(null);

        if (realEstateType != null && !realEstateType.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get(REAL_ESTATE_TYPE).in(realEstateType));
        }

        if (tradeType != null && !tradeType.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get(TRADE_TYPE).in(tradeType));
        }

        if (minPrice != null) {
            int pri = Integer.parseInt(minPrice);
            spec = spec.and(
                ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(PRICE), pri)));
        }

        if (maxPrice != null) {
            int pri = Integer.parseInt(maxPrice);
            spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                    criteriaBuilder.lessThan(root.get(PRICE), pri),
                    criteriaBuilder.greaterThan(root.get(PRICE), 0)
                )
            );
        }

        Page<Article> articlePage = articleRepository.findAll(spec, pageable);
        return articlePage.map(ArticleResponse::of);
    }

    public List<RealEstateTypeSummaryResponse> getRealEstateTypeSummary(String period) {
        LocalDate from = calculateStartDate(period);

        List<RealEstateTypeRatioProjection> realEstateTypeRatioProjections = articleRepository.countByRealEstateTypeSince(
            from);

        long realEstateTotalCount = realEstateTypeRatioProjections.stream()
            .mapToLong(RealEstateTypeRatioProjection::getCount)
            .sum();
        return realEstateTypeRatioProjections.stream()
            .map(it -> RealEstateTypeSummaryResponse.builder()
                .type(it.getType())
                .ratio((it.getCount() * 100.0) / realEstateTotalCount)
                .build())
            .toList();
    }

    public List<TradeTypeSummaryResponse> getTradeTypeSummary(String period) {
        LocalDate from = calculateStartDate(period);

        List<TradeTypeRatioProjection> tradeTypeRatioProjections = articleRepository.countByTradeTypeSince(from);

        long tradeTotalCount = tradeTypeRatioProjections.stream().mapToLong(TradeTypeRatioProjection::getCount).sum();
        return tradeTypeRatioProjections.stream()
            .map(it -> TradeTypeSummaryResponse.builder()
                .type(it.getType())
                .ratio((it.getCount() * 100.0) / tradeTotalCount)
                .build())
            .toList();
    }

    private LocalDate calculateStartDate(String period) {
        LocalDate now = LocalDate.now();

        return switch (period) {
            case "daily" -> now.minusDays(1);
            case "weekly" -> now.minusWeeks(1);
            case "monthly" -> now.minusMonths(1);
            default -> throw new CustomException(ErrorCode.VALIDATION_FAILED);
        };
    }
}

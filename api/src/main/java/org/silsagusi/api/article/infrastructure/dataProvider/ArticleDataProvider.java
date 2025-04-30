package org.silsagusi.api.article.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ArticleRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleDataProvider {

	private static final String REAL_ESTATE_TYPE = "articleType";
	private static final String TRADE_TYPE = "tradeType";
	private static final String PRICE = "price";

	private final ArticleRepository articleRepository;

	public Specification<Article> getArticleSpec(
		List<String> realEstateType,
		List<String> tradeType,
		String minPrice,
		String maxPrice
	) {
		Specification<Article> spec = Specification.where(null);

		if (realEstateType != null && !realEstateType.isEmpty()) {
			spec = spec.and((root, query, criteriaBuilder)
				-> root.get(REAL_ESTATE_TYPE).in(realEstateType));
		}

		if (tradeType != null && !tradeType.isEmpty()) {
			spec = spec.and((root, query, criteriaBuilder)
				-> root.get(TRADE_TYPE).in(tradeType));
		}

		if (minPrice != null) {
			int pri = Integer.parseInt(minPrice);
			spec = spec.and(
				((root, query, criteriaBuilder)
					-> criteriaBuilder.greaterThan(root.get(PRICE), pri)));
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
		return spec;
	}
	public Page<Article> getArticlesByRegionPrefix(
		String regionPrefix,
		Pageable pageable
	) {
		return articleRepository.findByBjdCodeStartingWith(regionPrefix, pageable);
	}

	public Page<Article> getArticlePage(
		Specification<Article> spec,
		Pageable pageable
	) {
		return articleRepository.findAll(spec, pageable);
	}

	public List<ArticleTypeRatioProjection> getRealEstateTypeRatio(LocalDate from) {
		return articleRepository.countByArticleTypeSince(from);
	}

	public List<ArticleTypeRatioProjection> getTradeTypeRatio(LocalDate from) {
		return articleRepository.countByTradeTypeSince(from);
	}

	public long sumArticleCount(List<ArticleTypeRatioProjection> articleTypeRatioProjections) {
		return articleTypeRatioProjections.stream()
			.mapToLong(ArticleTypeRatioProjection::getCount)
			.sum();
	}

	public LocalDate calculateStartDate(String period) {
		LocalDate now = LocalDate.now();

		return switch (period) {
			case "daily" -> now.minusDays(1);
			case "weekly" -> now.minusWeeks(1);
			case "monthly" -> now.minusMonths(1);
			default -> throw new CustomException(ErrorCode.VALIDATION_FAILED);
		};
	}
    /**
     * 화면에 보이는 영역(bounds) 내 매물만 페이징 조회
     *
     * @param swLat 화면 남서쪽 위도
     * @param neLat 화면 북동쪽 위도
     * @param swLng 화면 남서쪽 경도
     * @param neLng 화면 북동쪽 경도
     * @param regionPrefix 지역 코드 접두사 (optional)
     * @param pageable 페이징 및 정렬 정보
     * @return 지정된 영역 내 매물 Page
     */
    public Page<Article> getArticlesByBounds(
        Double swLat, Double neLat, Double swLng, Double neLng,
        String regionPrefix,
        Pageable pageable
    ) {
        if (regionPrefix != null && !regionPrefix.isEmpty()) {
            return articleRepository.findByLatitudeBetweenAndLongitudeBetweenAndBjdCodeStartingWith(
                swLat, neLat, swLng, neLng, regionPrefix, pageable
            );
        } else {
            return articleRepository.findAll(
                Specification.where((root, query, cb) -> cb.and(
                    cb.between(root.get("latitude"), swLat, neLat),
                    cb.between(root.get("longitude"), swLng, neLng)
                )),
                pageable
            );
        }
    }
}

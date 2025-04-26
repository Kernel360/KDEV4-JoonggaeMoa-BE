package org.silsagusi.api.article.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ArticleRepository;
import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
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
		return spec;
	}

	public Page<Article> getArticlePage(Specification<Article> spec, Pageable pageable) {
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
}

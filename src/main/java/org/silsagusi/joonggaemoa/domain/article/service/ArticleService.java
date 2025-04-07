package org.silsagusi.joonggaemoa.domain.article.service;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Article;
import org.silsagusi.joonggaemoa.domain.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.domain.article.service.dto.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
		Long minPrice,
		Long maxPrice
	) {
		Specification<Article> spec = Specification.where(null);

		if (realEstateType != null && !realEstateType.isEmpty()) {
			spec = spec.and((root, query, criteriaBuilder) -> root.get(REAL_ESTATE_TYPE).in(realEstateType));
		}

		if (tradeType != null && !tradeType.isEmpty()) {
			spec = spec.and((root, query, criteriaBuilder) -> root.get(TRADE_TYPE).in(tradeType));
		}

		if (minPrice != null) {
			spec = spec.and(
				((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(PRICE), minPrice)));
		}

		if (maxPrice != null) {
			spec = spec.and((root, query, criteriaBuilder) ->
				criteriaBuilder.and(
					criteriaBuilder.lessThan(root.get(PRICE), maxPrice),
					criteriaBuilder.greaterThan(root.get(PRICE), 0)
				)
			);
		}

		Page<Article> articlePage = articleRepository.findAll(spec, pageable);
		return articlePage.map(ArticleResponse::of);
	}
}

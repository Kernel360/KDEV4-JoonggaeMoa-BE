package org.silsagusi.api.article.infrastructure.dataprovider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ArticleRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	public Article getArticleInfo(Long articleId) {
		return articleRepository.findArticleById(articleId);
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

	public List<ArticleTypeRatioProjection> getRealEstateTypeRatio(LocalDate from) {
		return articleRepository.countByArticleTypeSince(from);
	}

	public List<ArticleTypeRatioProjection> getTradeTypeRatio(LocalDate from) {
		return articleRepository.countByTradeTypeSince(from);
	}


}

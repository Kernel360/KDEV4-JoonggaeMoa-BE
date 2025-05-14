package org.silsagusi.api.article.application.validator;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.repository.ArticleRepository;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleValidator {

	private final ArticleRepository articleRepository;

	public void validateArticleExists(Long articleId) {

		if (articleId == null) {
			throw new CustomException(ErrorCode.MISSING_REQUIRED_VALUE, ArticleValidator.class);
		}

		if (!articleRepository.existsById(articleId)) {
			throw new CustomException(ErrorCode.NOT_FOUND_ARTICLE, ArticleValidator.class, articleId);
		}

	}
}

package org.silsagusi.batch.application;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.core.domain.article.Article;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleValidator {

	private final ArticleRepository articleRepository;

	public boolean validateExist(Article article) {
		return articleRepository.findFirstByArticleCode(article.getArticleCode()).isEmpty();
	}
}

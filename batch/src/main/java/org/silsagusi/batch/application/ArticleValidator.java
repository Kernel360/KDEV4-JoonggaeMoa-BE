package org.silsagusi.batch.application;

import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArticleValidator {

	private final ArticleRepository articleRepository;
	private final ComplexRepository complexRepository;

	public boolean validateExist(Article article) {
		return articleRepository.findFirstByArticleCode(article.getArticleCode()).isEmpty();
	}

	public boolean validateExist(Complex complex) {
		return complexRepository.findFirstByComplexCode(complex.getComplexCode()).isEmpty();
	}
}

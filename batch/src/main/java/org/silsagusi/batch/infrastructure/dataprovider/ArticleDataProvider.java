package org.silsagusi.batch.infrastructure.dataprovider;

import java.util.List;

import org.silsagusi.batch.infrastructure.repository.ArticleRepository;
import org.silsagusi.core.domain.article.Article;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}
}

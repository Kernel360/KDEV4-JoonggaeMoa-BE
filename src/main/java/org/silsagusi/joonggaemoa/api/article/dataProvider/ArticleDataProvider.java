package org.silsagusi.joonggaemoa.api.article.dataProvider;

import java.util.List;

import org.silsagusi.joonggaemoa.core.domain.article.Article;
import org.silsagusi.joonggaemoa.core.domain.article.infrastructure.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleDataProvider {

	private final ArticleRepository articleRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}
}

package org.silsagusi.api.article.application.mapper;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.core.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleMapper {

	public ArticleResponse toResponse(Article article) {
		return ArticleResponse.toResponse(article);
	}

	public Page<ArticleResponse> toResponsePage(Page<Article> articlePage) {
		return articlePage.map(this::toResponse);
	}

	public List<ArticleResponse> toResponseList(List<Article> articleList) {
		return articleList.stream()
			.map(this::toResponse)
			.toList();
	}
}

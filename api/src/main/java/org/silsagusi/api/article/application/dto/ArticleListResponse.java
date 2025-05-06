package org.silsagusi.api.article.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.silsagusi.core.domain.article.Article;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ArticleListResponse {
	private List<ArticleResponse> content;
	private Long totalElements;
	private Integer totalPages;
	private Integer size;
	private Integer number;
	private Boolean last;

	public static ArticleListResponse toResponse(Page<Article> page) {
		return ArticleListResponse.builder()
			.content(page.getContent().stream()
				.map(ArticleResponse::toResponse)
				.toList())
			.totalElements(page.getTotalElements())
			.totalPages(page.getTotalPages())
			.size(page.getSize())
			.number(page.getNumber())
			.last(page.isLast())
			.build();
	}
}

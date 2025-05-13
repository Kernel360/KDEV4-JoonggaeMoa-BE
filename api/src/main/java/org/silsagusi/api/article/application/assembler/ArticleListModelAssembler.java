package org.silsagusi.api.article.application.assembler;

import org.jetbrains.annotations.NotNull;
import org.silsagusi.api.article.application.dto.ArticleListResponse;
import org.silsagusi.api.article.controller.ArticleController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ArticleListModelAssembler implements RepresentationModelAssembler<ArticleListResponse, EntityModel<ArticleListResponse>> {
	@NotNull
	@Override
	public EntityModel<ArticleListResponse> toModel(@NotNull ArticleListResponse articleListResponse) {
		return EntityModel.of(
			articleListResponse,
			linkTo(ArticleController.class)
				.slash("api/articles/search")
				.withSelfRel()
		);
	}
}

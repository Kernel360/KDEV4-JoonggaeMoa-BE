package org.silsagusi.api.article.application.assembler;

import org.jetbrains.annotations.NotNull;
import org.silsagusi.api.article.application.dto.ArticleResponse;
import org.silsagusi.api.article.controller.ArticleController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArticleModelAssembler implements RepresentationModelAssembler<ArticleResponse, EntityModel<ArticleResponse>> {

	@NotNull
	@Override
	public EntityModel<ArticleResponse> toModel(@NotNull ArticleResponse articleResponse) {
		return EntityModel.of(articleResponse,
			linkTo(methodOn(ArticleController.class).getArticleById(articleResponse.getId())).withSelfRel(),
			linkTo(ArticleController.class)
				.slash("api/articles")
				.withRel("articles")
		);
	}
}

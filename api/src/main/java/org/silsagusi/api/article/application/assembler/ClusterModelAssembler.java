package org.silsagusi.api.article.application.assembler;

import org.jetbrains.annotations.NotNull;
import org.silsagusi.api.article.controller.ArticleController;
import org.silsagusi.api.article.controller.request.ClusterRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClusterModelAssembler implements RepresentationModelAssembler<ClusterRequest, EntityModel<ClusterRequest>> {
	@NotNull
	@Override
	public EntityModel<ClusterRequest> toModel(@NotNull ClusterRequest clusterRequest) {
		return EntityModel.of(clusterRequest,
			linkTo(methodOn(ArticleController.class)
				.getClusters(clusterRequest))
				.withSelfRel()
		);
	}
}

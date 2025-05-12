package org.silsagusi.api.article.controller.request;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;

@Data
public class ArticleListRequest {
	@Valid
	private ArticleSearchCriteria criteria;
	private int page = 0;
	private int size = 20;
	private String sortBy = "confirmedAt";
	private Direction direction = Direction.DESC;
}
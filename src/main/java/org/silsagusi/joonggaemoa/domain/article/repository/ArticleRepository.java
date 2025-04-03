package org.silsagusi.joonggaemoa.domain.article.repository;

import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientArticleResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ClientArticleResponse.ArticleList, Long> {
}

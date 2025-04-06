package org.silsagusi.joonggaemoa.domain.article.repository;

import org.silsagusi.joonggaemoa.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

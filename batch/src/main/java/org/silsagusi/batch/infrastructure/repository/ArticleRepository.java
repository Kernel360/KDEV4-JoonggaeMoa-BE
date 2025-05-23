package org.silsagusi.batch.infrastructure.repository;

import java.util.Optional;

import org.silsagusi.core.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	Optional<Object> findFirstByArticleCode(String articleCode);
}

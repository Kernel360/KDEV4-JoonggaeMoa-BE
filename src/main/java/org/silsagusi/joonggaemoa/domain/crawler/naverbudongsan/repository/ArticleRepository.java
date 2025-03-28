package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}

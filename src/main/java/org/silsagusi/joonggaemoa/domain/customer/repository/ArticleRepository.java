package org.silsagusi.joonggaemoa.domain.customer.repository;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

}

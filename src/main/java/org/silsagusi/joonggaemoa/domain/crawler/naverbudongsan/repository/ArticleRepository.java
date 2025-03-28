package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Article findByPrc(String price);
    public Article findByRentPrc(String rentPrice);

    public default Article saveAll(Article articles) {
        return new Article();
    }
}

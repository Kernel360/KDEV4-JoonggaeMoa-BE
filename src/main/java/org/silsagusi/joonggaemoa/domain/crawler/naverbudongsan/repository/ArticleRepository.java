package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // 필요에 따라 커스텀 쿼리 메서드를 추가한다
}

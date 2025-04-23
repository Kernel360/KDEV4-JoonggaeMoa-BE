package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

	@Query("""
		    SELECT a.building_type AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmed_at >= :startDate
		    GROUP BY a.building_type
		""")
	List<ArticleTypeRatioProjection> countByArticleTypeSince(LocalDate startDate);

	// 거래 방식(TradeType) 비율
	@Query("""
		    SELECT a.trade_type AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmed_at >= :startDate
		    GROUP BY a.trade_type
		""")
	List<ArticleTypeRatioProjection> countByTradeTypeSince(LocalDate startDate);
}

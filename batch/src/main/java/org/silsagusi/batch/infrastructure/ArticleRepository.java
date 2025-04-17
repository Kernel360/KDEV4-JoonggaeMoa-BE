package org.silsagusi.batch.infrastructure;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

	@Query("""
		    SELECT a.realEstateType AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmedAt >= :startDate
		    GROUP BY a.realEstateType
		""")
	List<ArticleTypeRatioProjection> countByRealEstateTypeSince(LocalDate startDate);

	// 거래 방식(TradeType) 비율
	@Query("""
		    SELECT a.tradeType AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmedAt >= :startDate
		    GROUP BY a.tradeType
		""")
	List<ArticleTypeRatioProjection> countByTradeTypeSince(LocalDate startDate);
}

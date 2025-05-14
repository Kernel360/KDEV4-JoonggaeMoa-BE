package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	Article findArticleById(Long articleId);

	// 매물 타입(BuildingType) 비율
	@Query("""
		SELECT a.buildingType AS type, COUNT(a) AS count
		FROM Article a
		WHERE a.confirmedAt >= :startDate
		GROUP BY a.buildingType
		""")
	List<ArticleTypeRatioProjection> countByArticleTypeSince(LocalDate startDate);

	// 거래 방식(TradeType) 비율
	@Query("""
		SELECT a.tradeType AS type, COUNT(a) AS count
		FROM Article a
		WHERE a.confirmedAt >= :startDate
		GROUP BY a.tradeType
		""")
	List<ArticleTypeRatioProjection> countByTradeTypeSince(LocalDate startDate);
}

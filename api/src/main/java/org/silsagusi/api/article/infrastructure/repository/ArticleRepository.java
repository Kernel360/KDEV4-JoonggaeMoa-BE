package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.application.dto.ClusterInfo;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
	Page<Article> findByBjdCodeStartingWith(String regionPrefix, Pageable pageable);

	// 화면에 보이는 영역 내 매물만 페이징 조회
	Page<Article> findByLatitudeBetweenAndLongitudeBetweenAndBjdCodeStartingWith(
	    Double swLat, Double neLat, Double swLng, Double neLng, String regionPrefix, Pageable pageable
	);

	// 화면 영역 내 격자별 클러스터 요약 (centroid + count)
	//precision: 소수점 자릿수 (예: 3이면 0.001도 단위로 그룹)
    @Query(value = """
        SELECT
          AVG(latitude) AS lat,
          AVG(longitude) AS lng,
          COUNT(*) AS count
        FROM articles
        WHERE latitude BETWEEN :swLat AND :neLat
          AND longitude BETWEEN :swLng AND :neLng
        GROUP BY
          FLOOR(latitude * POWER(10, :precision)),
          FLOOR(longitude * POWER(10, :precision))
        """, nativeQuery = true)
    List<ClusterInfo> findClustersByBounds(
        @Param("swLat") double swLat,
        @Param("neLat") double neLat,
        @Param("swLng") double swLng,
        @Param("neLng") double neLng,
        @Param("precision") int precision
    );

	// 매물 타입(BuildingType) 비율
	@Query("""
		    SELECT a.buildingType AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmedAt >= :startDate
		    GROUP BY a.buildingType
		""")
	List<ArticleTypeRatioProjection> countByArticleTypeSince(LocalDate startDate);

	// 거래 방식(TradeType) 비율
	@Query("""
		    SELECT a.tradeType AS type, COUNT(a) AS count
		    FROM articles a
		    WHERE a.confirmedAt >= :startDate
		    GROUP BY a.tradeType
		""")
	List<ArticleTypeRatioProjection> countByTradeTypeSince(LocalDate startDate);
}

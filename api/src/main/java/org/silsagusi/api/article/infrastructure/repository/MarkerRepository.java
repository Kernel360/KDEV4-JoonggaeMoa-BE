package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.infrastructure.dto.BoundingBoxInfo;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.silsagusi.core.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarkerRepository extends JpaRepository<Article, Long> {

	@Query(value = """

		SELECT
		a.article_id AS id,
		ST_AsGeoJSON(Point(ST_X(a.geom), ST_Y(a.geom))) AS geoJson
		FROM articles a
		WHERE
		ST_X(a.geom) BETWEEN :#{#box.swLat} AND :#{#box.neLat}
		AND
		ST_Y(a.geom) BETWEEN :#{#box.swLng} AND :#{#box.neLng}
	""", nativeQuery = true)
	List<MarkerProjection> findMarkers(
		@Param("box") BoundingBoxInfo box
	);

	@Query(value = """

		SELECT
		a.article_id AS id,
		ST_AsGeoJSON(Point(ST_X(a.geom), ST_Y(a.geom))) AS geoJson
	FROM articles a
	WHERE ST_X(a.geom) BETWEEN :#{#box.swLat} AND :#{#box.neLat}
		AND ST_Y(a.geom) BETWEEN :#{#box.swLng} AND :#{#box.neLng}
		AND (:isTradeTypeEmpty = TRUE OR a.trade_type IN :tradeType)
		AND (:isBuildingTypeCodeEmpty = TRUE OR a.building_type_code IN :buildingTypeCode)
		AND (:minSalePrice IS NULL OR a.price_sale >= :minSalePrice)
		AND (:maxSalePrice IS NULL OR a.price_sale <= :maxSalePrice)
		AND (:minRentPrice IS NULL OR a.price_rent >= :minRentPrice)
		AND (:maxRentPrice IS NULL OR a.price_rent <= :maxRentPrice)
	""", nativeQuery = true)
	List<MarkerProjection> findFilteredMarkers(
		@Param("box") BoundingBoxInfo box,
		@Param("isTradeTypeEmpty") boolean isTradeTypeEmpty,
		@Param("tradeType") List<String> tradeType,
		@Param("isBuildingTypeCodeEmpty") boolean isBuildingTypeCodeEmpty,
		@Param("buildingTypeCode") List<String> buildingTypeCode,
		@Param("minSalePrice") Long minSalePrice,
		@Param("maxSalePrice") Long maxSalePrice,
		@Param("minRentPrice") Long minRentPrice,
		@Param("maxRentPrice") Long maxRentPrice
	);
}

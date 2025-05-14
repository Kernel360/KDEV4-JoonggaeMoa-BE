package org.silsagusi.api.article.infrastructure.repository;

import java.util.List;

import org.silsagusi.api.article.infrastructure.dto.BoundingBoxInfo;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.api.article.infrastructure.dto.MarkerProjection;
import org.silsagusi.core.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClusterRepository extends JpaRepository<Article, Long> {

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
			ST_AsGeoJSON(Point(AVG(ST_X(a.geom)), AVG(ST_Y(a.geom)))) AS geoJson,
			COUNT(a.article_id) AS count
		FROM articles a
		WHERE
			ST_X(a.geom) BETWEEN :#{#box.swLat} AND :#{#box.neLat}
		AND
			ST_Y(a.geom) BETWEEN :#{#box.swLng} AND :#{#box.neLng}
		GROUP BY
			FLOOR(ST_X(a.geom) / :gridSize) * :gridSize,
			FLOOR(ST_Y(a.geom) / :gridSize) * :gridSize
		""", nativeQuery = true)
	List<ClusterProjection> findClusters(
		@Param("box") BoundingBoxInfo box,
		@Param("gridSize") double gridSize
	);
}

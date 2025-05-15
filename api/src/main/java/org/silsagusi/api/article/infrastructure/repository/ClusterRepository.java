package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.infrastructure.dto.BoundingBoxInfo;
import org.silsagusi.api.article.infrastructure.dto.ClusterProjection;
import org.silsagusi.core.domain.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClusterRepository extends JpaRepository<Article, Long> {

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

package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.core.domain.article.Article;

import java.util.List;

public interface ArticleCustomRepository {
	List<ClusterResponse> findClustersByBounds(double swLat, double neLat, double swLng, double neLng, int precision);

	List<Article> findArticlesByCluster(long latGroup, long lngGroup, int precision, int size, int offset);

	List<ClusterResponse> findClustersByMarker(double swLat, double neLat, double swLng, double neLng);

	List<ClusterResponse> findClustersByGu(double swLat, double neLat, double swLng, double neLng);

	List<ClusterResponse> findClustersBySi(double swLat, double neLat, double swLng, double neLng);
}
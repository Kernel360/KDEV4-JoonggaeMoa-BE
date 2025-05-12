package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.application.dto.ClusterSummaryResponse;
import org.silsagusi.core.domain.article.Article;

import java.util.List;

public interface ArticleCustomRepository {
	// 선택된 클러스터에 속한 매물 조회
	List<Article> findArticlesByCluster(
		long latGroup, long lngGroup, int precision, int size, int offset
	);

	// 마커 기반 클러스터링
	List<ClusterSummaryResponse> findClustersByMarker(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	);

	// 동(precision) 기반 클러스터링
	List<ClusterSummaryResponse> findClustersByBounds(
		double swLat, double neLat, double swLng, double neLng, long precision
	);

	// 구 기반 클러스터링
	List<ClusterSummaryResponse> findClustersByGu(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	);

	// 시 기반 클러스터링
	List<ClusterSummaryResponse> findClustersBySi(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	);
}
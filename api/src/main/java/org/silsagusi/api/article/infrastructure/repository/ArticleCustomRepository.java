package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.api.article.controller.request.ClusterRequest;
import org.silsagusi.core.domain.article.Article;

import java.util.List;

public interface ArticleCustomRepository {
	// 동(precision) 기반 클러스터링
	List<ClusterRequest> findClustersByBounds(double swLat, double neLat, double swLng, double neLng, int precision);

	// 선택된 클러스터에 속한 매물 조회
	List<Article> findArticlesByCluster(long latGroup, long lngGroup, int precision, int size, int offset);

	// 마커 기반 클러스터링
	List<ClusterRequest> findClustersByMarker(double swLat, double neLat, double swLng, double neLng);

	// 구 기반 클러스터링
	List<ClusterRequest> findClustersByGu(double swLat, double neLat, double swLng, double neLng);

	// 시 기반 클러스터링
	List<ClusterRequest> findClustersBySi(double swLat, double neLat, double swLng, double neLng);
}
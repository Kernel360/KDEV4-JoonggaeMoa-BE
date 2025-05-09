package org.silsagusi.api.article.infrastructure.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.application.dto.ClusterResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.QArticle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<ClusterResponse> findClustersByBounds(double swLat, double neLat, double swLng, double neLng, int precision) {
		QArticle article = QArticle.article;

		return queryFactory
			.select(Projections.constructor(
				ClusterResponse.class,
				article.latitude.avg(),
				article.longitude.avg(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(
				article.latitude.multiply(Math.pow(10, precision)).floor(),
				article.longitude.multiply(Math.pow(10, precision)).floor()
			)
			.fetch();
	}

	@Override
	public List<Article> findArticlesByCluster(long latGroup, long lngGroup, int precision, int size, int offset) {
		QArticle article = QArticle.article;

		return queryFactory
			.selectFrom(article)
			.where(
				article.latitude.multiply(Math.pow(10, precision)).floor().eq((double) latGroup),
				article.longitude.multiply(Math.pow(10, precision)).floor().eq((double) lngGroup)
			)
			.orderBy(article.id.asc())
			.offset(offset)
			.limit(size)
			.fetch();
	}

	@Override
	public List<ClusterResponse> findClustersByMarker(double swLat, double neLat, double swLng, double neLng) {
		QArticle article = QArticle.article;

		return queryFactory
			.select(Projections.constructor(
				ClusterResponse.class,
				article.latitude,
				article.longitude,
				queryFactory.select(article.count())
					.from(article)
					.where(
						article.latitude.between(swLat, neLat),
						article.longitude.between(swLng, neLng)
					)
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.fetch();
	}

	@Override
	public List<ClusterResponse> findClustersByGu(double swLat, double neLat, double swLng, double neLng) {
		QArticle article = QArticle.article;

		return queryFactory
			.select(Projections.constructor(
				ClusterResponse.class,
				article.latitude.avg(),
				article.longitude.avg(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(article.bjdCode.substring(0, 5))
			.fetch();
	}

	@Override
	public List<ClusterResponse> findClustersBySi(double swLat, double neLat, double swLng, double neLng) {
		QArticle article = QArticle.article;

		return queryFactory
			.select(Projections.constructor(
				ClusterResponse.class,
				article.latitude.avg(),
				article.longitude.avg(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(article.bjdCode.substring(0, 2))
			.fetch();
	}
}
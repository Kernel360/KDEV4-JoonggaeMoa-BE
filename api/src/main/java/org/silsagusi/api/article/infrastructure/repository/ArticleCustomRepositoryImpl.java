package org.silsagusi.api.article.infrastructure.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.api.article.application.dto.ClusterSummaryResponse;
import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.QArticle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Article> findArticlesByCluster(
		long latGroup, long lngGroup, int precision, int size, int offset
	) {
		QArticle article = QArticle.article;
		double factor = Math.pow(10, precision);

		return queryFactory
			.selectFrom(article)
			.where(
				article.latitude.multiply(factor).floor().eq((double) latGroup),
				article.longitude.multiply(factor).floor().eq((double) lngGroup)
			)
			.orderBy(article.id.asc())
			.offset(offset)
			.limit(size)
			.fetch();
	}

	@Override
	public List<ClusterSummaryResponse> findClustersByMarker(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	) {
		QArticle article = QArticle.article;
		double factor = Math.pow(10, zoomLevel - 1);

		return queryFactory
			.select(Projections.constructor(
				ClusterSummaryResponse.class,
				article.latitude.multiply(factor).floor().divide(factor).min(),
				article.longitude.multiply(factor).floor().divide(factor).min(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(
				article.latitude.multiply(factor).floor().divide(factor),
				article.longitude.multiply(factor).floor().divide(factor)
			)
			.fetch();
	}

	@Override
	public List<ClusterSummaryResponse> findClustersByBounds(
		double swLat, double neLat, double swLng, double neLng, long precision
	) {
		QArticle article = QArticle.article;
		double factor = Math.pow(10, precision);

		return queryFactory
			.select(Projections.constructor(
				ClusterSummaryResponse.class,
				article.latitude.multiply(factor).floor().divide(factor).min(),
				article.longitude.multiply(factor).floor().divide(factor).min(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(
				article.latitude.multiply(factor).floor().divide(factor),
				article.longitude.multiply(factor).floor().divide(factor)
			)
			.fetch();
	}

	@Override
	public List<ClusterSummaryResponse> findClustersByGu(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	) {
		QArticle article = QArticle.article;
		StringExpression guCode = article.bjdCode.substring(0, 5);

		return queryFactory
			.select(Projections.constructor(
				ClusterSummaryResponse.class,
				article.latitude.avg(),
				article.longitude.avg(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(guCode)
			.fetch();
	}

	@Override
	public List<ClusterSummaryResponse> findClustersBySi(
		double swLat, double neLat, double swLng, double neLng, long zoomLevel
	) {
		QArticle article = QArticle.article;
		StringExpression siCode = article.bjdCode.substring(0, 2);

		return queryFactory
			.select(Projections.constructor(
				ClusterSummaryResponse.class,
				article.latitude.avg(),
				article.longitude.avg(),
				article.count()
			))
			.from(article)
			.where(
				article.latitude.between(swLat, neLat),
				article.longitude.between(swLng, neLng)
			)
			.groupBy(siCode)
			.fetch();
	}
}
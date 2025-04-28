package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.projection.ArticleTypeRatioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

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
	/**
	 * Finds all Articles matching the specification, sorted and paged.
	 */
	default Page<Article> findAll(
		Specification<Article> spec,
		int page,
		int size,
		String sortBy,
		String direction
	) {
		// 화이트리스트 적용
		Set<String> allowedFields = Set.of("id", "priceSale", "confirmedAt");
		Set<String> allowedDirs = Set.of("asc", "desc");
		if (!allowedFields.contains(sortBy)) {
			sortBy = "id";
		}
		if (!allowedDirs.contains(direction.toLowerCase())) {
			direction = "desc";
		}
		Sort sortObj = direction.equalsIgnoreCase("asc")
			? Sort.by(sortBy).ascending()
			: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sortObj);
		return findAll(spec, pageable);
	}
}

package org.silsagusi.api.article.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.article.NaverLandScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RegionScrapStatusRepository extends JpaRepository<NaverLandScrapeStatus, Long> {

	List<NaverLandScrapeStatus> findTop50ByCompletedFalseOrderByIdAsc();

	List<NaverLandScrapeStatus> findTop1ByCompletedFalseOrderByIdAsc();

	@Modifying
	@Transactional
	@Query("update region_scrape_statuses s set s.completed = false, s.lastScrapedPage = 1 "
		+ "where s.lastScrapedAt < :cutoff")
	void resetAllScrapStatus(@Param("cutoff") LocalDateTime cutoff);
}

package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {

	List<ScrapeStatus> findTop50ByCompletedFalseOrderByIdAsc();

	List<ScrapeStatus> findTop1ByCompletedFalseOrderByIdAsc();

	@Query("update scrape_statuses s set s.completed = false, s.lastScrapedPage = 1 "
		+ "where s.lastScrapedAt < :cutoff")
	void resetAllScrapeStatus(@Param("cutoff") LocalDateTime cutoff);
}

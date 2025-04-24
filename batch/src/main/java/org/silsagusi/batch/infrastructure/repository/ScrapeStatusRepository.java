package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {

	List<ScrapeStatus> findTop50ByCompletedFalseOrderByIdAsc();

	List<ScrapeStatus> findTop1ByCompletedFalseOrderByIdAsc();



	@Query("update scrape_statuses s set s.completed = false, s.lastScrapedPage = 1 "
		+ "where s.lastScrapedAt < :cutoff")
	void resetAllScrapeStatus(@Param("cutoff") LocalDateTime cutoff);

	@Modifying
	@Transactional
	@Query(
		value = "INSERT INTO scrape_statuses(region_id, last_scraped_page, completed, last_scraped_at, source) " +
				"VALUES(:regionId, :page, :completed, :lastAt, :source) " +
				"ON DUPLICATE KEY UPDATE " +
				"last_scraped_page = VALUES(last_scraped_page), " +
				"completed = VALUES(completed), " +
				"last_scraped_at = VALUES(last_scraped_at)",
		nativeQuery = true
	)
	void upsertNative(
		@Param("regionId") Long regionId,
		@Param("page") Integer page,
		@Param("completed") Boolean completed,
		@Param("lastAt") LocalDateTime lastAt,
		@Param("source") String source
	);
}

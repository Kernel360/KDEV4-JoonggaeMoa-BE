package org.silsagusi.batch.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {

	List<ScrapeStatus> findTop10BySourceAndCompletedFalseOrderByIdAsc(String source);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("""
	    UPDATE ScrapeStatus s
	    SET s.completed = false,
	        s.failed = false,
	        s.errorMessage = null,
	        s.lastScrapedAt = null,
	        s.lastScrapedPage = 1
	    WHERE s.lastScrapedAt < :cutoff
	    """)
	void resetAllScrapeStatus(@Param("cutoff") LocalDateTime cutoff);
}

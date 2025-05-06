package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.ScrapeStatus;
import org.silsagusi.core.domain.article.enums.Platform;
import org.silsagusi.core.domain.article.enums.ScrapeStatusType;
import org.silsagusi.core.domain.article.enums.ScrapeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapeStatusRepository extends JpaRepository<ScrapeStatus, Long> {

	// List<ScrapeStatus> findTop10BySourceAndCompletedFalseOrderByIdAsc(String source);
	//
	// List<ScrapeStatus> findTop1ByCompletedFalseOrderByIdAsc();

	ScrapeStatus findFirstByPlatformAndTargetTypeAndStatusOrderByIdAsc(Platform platform,
		ScrapeTargetType targetType, ScrapeStatusType status);

	// @Modifying(clearAutomatically = true)
	// @Transactional
	// @Query("""
	// 	UPDATE ScrapeStatus s
	// 	SET s.completed = false,
	// 	    s.failed = false,
	// 	    s.errorMessage = null,
	// 	    s.lastScrapedAt = null,
	// 	    s.lastScrapedPage = 1
	// 	WHERE s.lastScrapedAt < :cutoff
	// 	""")
	// void resetAllScrapeStatus(@Param("cutoff") LocalDateTime cutoff);
}

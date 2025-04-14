package org.silsagusi.joonggaemoa.domain.article.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RegionScrapStatusRepository extends JpaRepository<RegionScrapStatus, Long> {

	List<RegionScrapStatus> findTop50ByCompletedFalseOrderByIdAsc();

	List<RegionScrapStatus> findTop1ByCompletedFalseOrderByIdAsc();

	@Modifying
	@Transactional
	@Query("update region_scrap_statuses s set s.completed = false, s.lastScrapedPage = 1 "
		+ "where s.lastScrapedAt < :cutoff")
	void resetAllScrapStatus(@Param("cutoff") LocalDateTime cutoff);

	List<RegionScrapStatus> findByRegion_CortarNo(String cortarNo);
}

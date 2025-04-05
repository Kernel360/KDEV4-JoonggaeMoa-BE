package org.silsagusi.joonggaemoa.domain.article.repository;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionScrapStatusRepository extends JpaRepository<RegionScrapStatus, Long> {

	List<RegionScrapStatus> findTop10ByCompletedFalseOrderByIdAsc();
}

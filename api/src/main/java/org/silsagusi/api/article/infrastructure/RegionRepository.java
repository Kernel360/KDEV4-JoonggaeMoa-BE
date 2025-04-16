package org.silsagusi.api.article.infrastructure;

import java.util.List;

import org.silsagusi.core.domain.article.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findAllByCortarNoIn(List<String> cortarNos);
}
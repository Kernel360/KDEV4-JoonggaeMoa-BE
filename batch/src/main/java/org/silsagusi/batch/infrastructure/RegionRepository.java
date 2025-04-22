package org.silsagusi.batch.infrastructure;

import org.silsagusi.core.domain.article.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

	List<Region> findByCortarType(String type);
}
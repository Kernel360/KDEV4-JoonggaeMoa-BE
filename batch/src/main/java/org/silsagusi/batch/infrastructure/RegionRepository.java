package org.silsagusi.batch.infrastructure;

import java.util.List;

import org.silsagusi.core.domain.article.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

	List<Region> findByCortarType(String type);
}
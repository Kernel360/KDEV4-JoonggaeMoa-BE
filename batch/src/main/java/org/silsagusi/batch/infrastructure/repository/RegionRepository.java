package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Region findByCortarName(String cortarName);
}
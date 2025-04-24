package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

	List<Region> findAllByCortarType(String type);
}
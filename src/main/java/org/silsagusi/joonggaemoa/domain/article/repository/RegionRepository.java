package org.silsagusi.joonggaemoa.domain.article.repository;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findAllByCortarNoIn(List<String> cortarNos);
}
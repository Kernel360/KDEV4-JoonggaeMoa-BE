package org.silsagusi.joonggaemoa.api.article.repository;

import java.util.List;

import org.silsagusi.joonggaemoa.api.article.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
	List<Region> findAllByCortarNoIn(List<String> cortarNos);
}
package org.silsagusi.joonggaemoa.api.article.repository;

import org.silsagusi.joonggaemoa.api.article.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findAllByCortarNoIn(List<String> cortarNos);
}
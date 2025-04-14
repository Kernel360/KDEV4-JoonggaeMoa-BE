package org.silsagusi.joonggaemoa.domain.article.repository;

import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	@Query("SELECT r FROM regions r WHERE r.cortarNo IN :cortarNos")
	List<Region> findAllByCortarNoIn(List<String> cortarNos);
}
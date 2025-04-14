package org.silsagusi.joonggaemoa.domain.article.repository;

import java.util.List;
import java.util.Optional;

import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.service.dto.RegionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	@Query("SELECT r FROM regions r WHERE r.cortarNo = :cortarNo")
	List<Region> findAllByCortarNoIn(List<String> cortarNos);

	List<RegionResponse> findByCortarNo(String cortarNo);

	Optional<Region> findRegionEntityByCortarNo(String cortarNo);
}
package org.silsagusi.joonggaemoa.domain.article.repository;

import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientComplexResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexRepository extends JpaRepository<ClientComplexResponse.ComplexList, Long> {
}
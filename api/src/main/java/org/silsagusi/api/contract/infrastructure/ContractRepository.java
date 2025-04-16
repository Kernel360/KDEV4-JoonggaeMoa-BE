package org.silsagusi.api.contract.infrastructure;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ContractRepository extends JpaRepository<Contract, String> {
	Page<Contract> findAllByCustomerLandlord_AgentId(Long agentId, Pageable pageable);

	Page<Contract> findByExpiredAt(LocalDate expiredAt, Pageable pageable);

	@Query("SELECT COUNT(c) FROM contracts c WHERE c.customerLandlord.agent.id = :agentId " +
		"AND c.createdAt <= :today AND c.expiredAt >= :today")
	long countInProgress(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	// 특정 날짜에 생성된 계약 수
	long countByCustomerLandlord_Agent_IdAndCreatedAt(Long agentId, LocalDate createdAt);
}

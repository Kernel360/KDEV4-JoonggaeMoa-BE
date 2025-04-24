package org.silsagusi.api.contract.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, String> {
	Page<Contract> findAllByCustomerLandlord_AgentIdAndDeletedAtIsNull(Long agentId, Pageable pageable);

	List<Contract> findByExpiredAtAndDeletedAtIsNull(LocalDate expiredAt);

	@Query("SELECT COUNT(c) FROM contracts c WHERE c.customerLandlord.agent.id = :agentId " +
		"AND c.startedAt <= :today AND c.expiredAt >= :today AND c.deletedAt IS NULL"
	)
	long countInProgress(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	// 특정 날짜에 생성된 계약 수
	long countByCustomerLandlord_Agent_IdAndStartedAt(Long agentId, LocalDate startedAt);

	Optional<Contract> findByIdAndDeletedAtIsNull(String contractId);

	@Query("""
		    SELECT c FROM contracts c
		    WHERE 
		        (c.customerLandlord = :customer OR c.customerTenant = :customer)
		        AND (
		            (c.createdAt BETWEEN :start AND :end)
		            OR
		            (c.expiredAt BETWEEN :start AND :end)
		        )
				        AND c.deletedAt IS NULL
		""")
	List<Contract> findContractsByCustomerAndDateRange(
		@Param("customer") Customer customer,
		@Param("start") LocalDate start,
		@Param("end") LocalDate end
	);
}

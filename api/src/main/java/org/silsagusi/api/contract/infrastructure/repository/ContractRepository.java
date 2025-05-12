package org.silsagusi.api.contract.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, String>, ContractCustomRepository {

	@EntityGraph(attributePaths = {"agent", "customerLandlord", "customerTenant"})
	List<Contract> findByExpiredAtAndDeletedAtIsNull(LocalDate expiredAt);

	@Query("SELECT COUNT(c) FROM Contract c WHERE c.agent.id = :agentId " +
		"AND c.startedAt <= :today AND c.expiredAt >= :today AND c.deletedAt IS NULL")
	long countInProgress(@Param("agentId") Long agentId, @Param("today") LocalDate today);

	@EntityGraph(attributePaths = {"agent", "customerLandlord", "customerTenant"})
	Optional<Contract> findByIdAndDeletedAtIsNull(String contractId);

	@EntityGraph(attributePaths = {"customerLandlord", "customerTenant"})
	List<Contract> findAllByAgent_IdAndExpiredAtBeforeAndDeletedAtIsNull(Long agentId, LocalDate expiredAt);
}

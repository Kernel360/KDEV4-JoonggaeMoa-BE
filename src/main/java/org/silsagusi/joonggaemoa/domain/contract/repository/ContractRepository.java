package org.silsagusi.joonggaemoa.domain.contract.repository;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
	Page<Contract> findAllByCustomerLandlord_AgentId(Long agentId, Pageable pageable);

	Page<Contract> findByExpiredAt(LocalDate expiredAt, Pageable pageable);
}

package org.silsagusi.api.contract.infrastructure.repository;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractCustomRepository {
	Page<Contract> findContracts(Long agentId, String keyword, Pageable pageable);
}

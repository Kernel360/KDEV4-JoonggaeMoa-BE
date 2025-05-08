package org.silsagusi.api.contract.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;

import org.silsagusi.core.domain.contract.entity.Contract;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContractCustomRepository {
	Page<Contract> findContracts(Long agentId, String keyword, Pageable pageable);

	List<Contract> findContractsByCustomerAndDateRange(Customer customer, LocalDate start, LocalDate end);
}

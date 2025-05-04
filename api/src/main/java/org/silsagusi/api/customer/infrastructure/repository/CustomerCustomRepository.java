package org.silsagusi.api.customer.infrastructure.repository;

import org.silsagusi.core.domain.customer.entity.Customer;
import org.springframework.data.domain.Slice;

public interface CustomerCustomRepository {
	Slice<Customer> findCustomerSlice(Long agentId, Long cursor, String keyword);
}

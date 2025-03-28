package org.silsagusi.joonggaemoa.domain.customer.repository;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findByPhone(String phone);
}

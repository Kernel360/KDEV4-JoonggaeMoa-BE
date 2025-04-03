package org.silsagusi.joonggaemoa.domain.customer.repository;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);

    Page<Customer> findAll(Pageable pageable);
}

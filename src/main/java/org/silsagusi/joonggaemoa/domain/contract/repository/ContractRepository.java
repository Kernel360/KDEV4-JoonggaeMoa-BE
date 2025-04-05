package org.silsagusi.joonggaemoa.domain.contract.repository;

import org.silsagusi.joonggaemoa.domain.contract.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Page<Contract> findAll(Pageable pageable);

    Page<Contract> findByExpiredAt(LocalDate expiredAt, Pageable pageable);
}

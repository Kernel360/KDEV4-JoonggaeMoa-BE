package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.Complex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComplexRepository extends JpaRepository<Complex, Long>, JpaSpecificationExecutor<Complex> {
}

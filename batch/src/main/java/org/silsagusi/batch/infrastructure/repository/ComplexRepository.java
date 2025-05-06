package org.silsagusi.batch.infrastructure.repository;

import org.silsagusi.core.domain.article.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplexRepository extends JpaRepository<Complex, Long> {
	List<Complex> findByComplexName(String complexName);
}

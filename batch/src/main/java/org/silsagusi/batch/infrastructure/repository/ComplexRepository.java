package org.silsagusi.batch.infrastructure.repository;

import java.util.Optional;

import org.silsagusi.core.domain.article.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexRepository extends JpaRepository<Complex, Long> {

	Optional<Complex> findFirstByComplexCode(String complexCode);
}

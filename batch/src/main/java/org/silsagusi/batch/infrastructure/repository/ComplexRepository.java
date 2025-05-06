package org.silsagusi.batch.infrastructure.repository;

import java.util.Optional;

import org.silsagusi.core.domain.article.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplexRepository extends JpaRepository<Complex, Long> {
<<<<<<< HEAD
	Optional<Complex> findFirstByComplexCode(String complexCode);
=======
	List<Complex> findByComplexName(String complexName);
>>>>>>> develop
}

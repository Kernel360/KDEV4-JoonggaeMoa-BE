package org.silsagusi.api.article.infrastructure.repository;

import org.silsagusi.core.domain.article.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexRepository extends JpaRepository<Complex, Long> {
}
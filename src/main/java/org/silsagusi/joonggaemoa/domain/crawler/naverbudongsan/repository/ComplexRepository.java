package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.repository;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplexRepository extends JpaRepository<Complex, Long> {
    public Complex findByIsalePrcMin(String minPrice);

    public Complex findByIsalePrcMax(String maxPrice);

    public default Complex saveAll(Complex complexes) {
        return new Complex();
    }
}
package org.silsagusi.api.inquiry.infrastructure.repository;

import java.util.Optional;

import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
	@EntityGraph(attributePaths = "answers")
	Optional<Inquiry> findByIdAndDeletedAtIsNull(Long inquiryId);

	Page<Inquiry> findAllByDeletedAtIsNull(Pageable pageable);
}

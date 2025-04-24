package org.silsagusi.api.inquiry.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
	Optional<Inquiry> findByIdAndDeletedAtIsNull(Long inquiryId);

	List<Inquiry> findAllByDeletedAtIsNull();
}

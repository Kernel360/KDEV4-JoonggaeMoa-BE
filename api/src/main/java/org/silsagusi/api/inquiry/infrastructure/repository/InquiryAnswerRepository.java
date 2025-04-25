package org.silsagusi.api.inquiry.infrastructure.repository;

import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryAnswerRepository extends JpaRepository<InquiryAnswer, Long> {
}

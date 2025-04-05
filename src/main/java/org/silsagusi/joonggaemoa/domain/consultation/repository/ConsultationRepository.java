package org.silsagusi.joonggaemoa.domain.consultation.repository;

import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findAllByDateBetween(LocalDateTime startdate, LocalDateTime endDate);

    Integer countByDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Page<Consultation> findByDateBetweenAndConsultationStatus(
        LocalDateTime start, LocalDateTime end,
        Consultation.ConsultationStatus status,
        Pageable pageable
    );
}

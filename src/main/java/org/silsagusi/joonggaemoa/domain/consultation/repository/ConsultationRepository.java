package org.silsagusi.joonggaemoa.domain.consultation.repository;

import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findAllByConsultationStatus(Consultation.ConsultationStatus consultationStatus);


}

package org.silsagusi.joonggaemoa.api.consultation.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.api.customer.domain.Customer;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "consultations")
@Getter
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private String purpose;

    private String interestProperty;

    private String interestLocation;

    private String contractType;

    private String assetStatus;

    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(name = "consultation_status")
    private ConsultationStatus consultationStatus;

    public enum ConsultationStatus {
        WAITING,     // 상담 예약 대기
        CONFIRMED,   // 예약 확정
        CANCELED,    // 예약 취소
        COMPLETED    // 진행 완료
    }

    public Consultation(
        Customer customer,
        LocalDateTime date,
        ConsultationStatus consultationStatus
    ) {
        this.customer = customer;
        this.date = date;
        this.consultationStatus = consultationStatus;
    }

    public void updateStatus(ConsultationStatus consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    public void updateConsultation(
        LocalDateTime date,
        String purpose,
        String interestProperty,
        String interestLocation,
        String contractType,
        String assetStatus,
        String memo,
        ConsultationStatus consultationStatus
    ) {
        this.date = date;
        this.purpose = purpose;
        this.interestProperty = interestProperty;
        this.interestLocation = interestLocation;
        this.contractType = contractType;
        this.assetStatus = assetStatus;
        this.memo = memo;
        this.consultationStatus = consultationStatus;
    }

}

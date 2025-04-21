package org.silsagusi.core.domain.consultation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.customer.entity.Customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "consultations")
@Getter
public class Consultation extends BaseEntity {

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

	private String memo;

	@Enumerated(EnumType.STRING)
	@Column(name = "consultation_status")
	private ConsultationStatus consultationStatus;

	private Consultation(
		Customer customer,
		LocalDateTime date,
		ConsultationStatus consultationStatus
	) {
		this.customer = customer;
		this.date = date;
		this.consultationStatus = consultationStatus;
	}

	public static Consultation create(Customer customer, LocalDateTime date, ConsultationStatus consultationStatus) {
		return new Consultation(customer, date, consultationStatus);
	}

	public void updateStatus(ConsultationStatus consultationStatus) {
		this.consultationStatus = consultationStatus;
	}

	public void updateConsultation(
		LocalDateTime date,
		String purpose,
		String memo
	) {
		this.date = date;
		this.purpose = purpose;
		this.memo = memo;
	}

	public enum ConsultationStatus {
		WAITING,     // 상담 예약 대기
		CONFIRMED,   // 예약 확정
		CANCELED,    // 예약 취소
		COMPLETED    // 진행 완료
	}

}

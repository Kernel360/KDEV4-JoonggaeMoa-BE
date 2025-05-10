package org.silsagusi.core.domain.consultation.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.consultation.enums.ConsultationStatus;
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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "consultations", indexes = {
	@Index(name = "idx_consultation_agent_date_status_deleted", columnList = "agent_id, date, consultation_status, deleted_at")
})
@Getter
public class Consultation extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "consultation_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = false)
	private Agent agent;

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

	private Consultation(Agent agent, Customer customer, LocalDateTime date, String purpose, String memo,
		ConsultationStatus consultationStatus) {
		this.agent = agent;
		this.customer = customer;
		this.date = date;
		this.purpose = purpose;
		this.memo = memo;
		this.consultationStatus = consultationStatus;
	}

	public static Consultation create(Agent agent, Customer customer, LocalDateTime date, String purpose, String memo,
		ConsultationStatus consultationStatus) {
		return new Consultation(agent, customer, date, purpose, memo, consultationStatus);
	}

	public void updateStatus(ConsultationStatus consultationStatus) {
		this.consultationStatus = consultationStatus;
	}

	public void updateConsultation(LocalDateTime date, String purpose, String memo) {
		this.date = date;
		this.purpose = purpose;
		this.memo = memo;
	}
}

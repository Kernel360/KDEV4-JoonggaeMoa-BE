package org.silsagusi.joonggaemoa.domain.consultation.entity;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@ToString
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

	private LocalDate date;

	private String purpose;

	private Integer interestProperty;

	private String interestLocation;

	private String contractType;

	private String assetStatus;

	private String memo;
}

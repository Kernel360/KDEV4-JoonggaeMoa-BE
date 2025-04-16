package org.silsagusi.joonggaemoa.core.domain.customer.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "customers")
@Getter
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;

	private String name;

	private LocalDate birthday;

	private String phone;

	private String email;

	private String job;

	private Boolean isVip;

	@Lob
	private String memo;

	private Boolean consent;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = false)
	private Agent agent;

	public Customer(
		String name,
		LocalDate birthday,
		String phone,
		String email,
		String job,
		Boolean isVip,
		String memo,
		Boolean consent,
		Agent agent
	) {
		this.name = name;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.job = job;
		this.isVip = isVip;
		this.memo = memo;
		this.consent = consent;
		this.agent = agent;
		createdAt = LocalDateTime.now();
	}

	public Customer(
		String name,
		String phone,
		String email,
		Boolean consent,
		Agent agent
	) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.consent = consent;
		this.agent = agent;
	}

	public void updateCustomer(
		String name,
		LocalDate birthday,
		String phone,
		String email,
		String job,
		Boolean isVip,
		String memo,
		Boolean consent
	) {
		this.name = name;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.job = job;
		this.isVip = isVip;
		this.memo = memo;
		this.consent = consent;
	}

}

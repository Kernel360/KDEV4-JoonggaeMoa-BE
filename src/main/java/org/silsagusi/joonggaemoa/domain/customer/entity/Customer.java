package org.silsagusi.joonggaemoa.domain.customer.entity;

import java.time.LocalDate;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = false)
	private Agent agent;

	public Customer(String name, LocalDate birthday, String phone, String email, String job, Boolean isVip,
		String memo, Boolean consent, Agent agent) {
		this.name = name;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.job = job;
		this.isVip = isVip;
		this.memo = memo;
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

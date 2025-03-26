package org.silsagusi.joonggaemoa.domain.contract.entity;

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
@Entity(name = "contracts")
@Getter
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_landlord")
	private Customer customerLandlord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_tenant")
	private Customer customerTenant;

}

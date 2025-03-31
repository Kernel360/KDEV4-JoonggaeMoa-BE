package org.silsagusi.joonggaemoa.domain.contract.entity;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "contracts")
@Getter
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "landlord_id")
	private Customer customerLandlord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tenant_id")
	private Customer customerTenant;

	@Column(name = "created_at")
	private LocalDate createdAt;

	@Column(name = "expired_at")
	private LocalDate expiredAt;

	private String url;

	public Contract(
		Customer customerLandlordId,
		Customer customerTenantId,
		LocalDate createdAt,
		LocalDate expiredAt,
		String url
	) {
		this.customerLandlord = customerLandlordId;
		this.customerTenant = customerTenantId;
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.url = url;
	}

	public void update(
		LocalDate createdAt,
		LocalDate expiredAt,
		String url
	) {
		this.createdAt = createdAt;
		this.expiredAt = expiredAt;
		this.url = url;
	}
}

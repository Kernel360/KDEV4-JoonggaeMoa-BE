package org.silsagusi.core.domain.contract.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.silsagusi.core.domain.customer.entity.Customer;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "contracts")
@Getter
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "contract_id")
	private String id;

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

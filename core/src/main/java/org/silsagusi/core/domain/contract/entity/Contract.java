package org.silsagusi.core.domain.contract.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.customer.entity.Customer;

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
public class Contract extends BaseEntity {

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

	@Column(name = "started_at")
	private LocalDate startedAt;

	@Column(name = "expired_at")
	private LocalDate expiredAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	private String url;

	private Contract(
		Customer customerLandlordId,
		Customer customerTenantId,
		LocalDate startedAt,
		LocalDate expiredAt,
		String url
	) {
		this.customerLandlord = customerLandlordId;
		this.customerTenant = customerTenantId;
		this.startedAt = startedAt;
		this.expiredAt = expiredAt;
		this.url = url;
	}

	public static Contract create(
		Customer customerLandlordId,
		Customer customerTenantId,
		LocalDate startedAt,
		LocalDate expiredAt,
		String url
	) {
		return new Contract(
			customerLandlordId,
			customerTenantId,
			startedAt,
			expiredAt,
			url
		);
	}

	public void markAsDeleted() {
		this.deletedAt = LocalDateTime.now();
	}

	public void update(
		LocalDate startedAt,
		LocalDate expiredAt,
		String url
	) {
		this.startedAt = startedAt;
		this.expiredAt = expiredAt;
		this.url = url;
	}
}

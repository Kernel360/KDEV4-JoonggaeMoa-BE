package org.silsagusi.core.domain.contract.entity;

import java.time.LocalDate;

import org.silsagusi.core.domain.BaseEntity;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "contracts", indexes = {
	@Index(name = "idx_contract_agent_expired_deleted", columnList = "agent_id, expired_at, deleted_at"),
	@Index(name = "idx_contract_agent_started_expired_deleted", columnList = "agent_id, started_at, expired_at, deleted_at")
})
@Getter
public class Contract extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "contract_id")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id")
	private Agent agent;

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

	private String url;

	private Contract(Agent agent, Customer customerLandlordId, Customer customerTenantId, LocalDate startedAt,
		LocalDate expiredAt, String url) {
		this.agent = agent;
		this.customerLandlord = customerLandlordId;
		this.customerTenant = customerTenantId;
		this.startedAt = startedAt;
		this.expiredAt = expiredAt;
		this.url = url;
	}

	public static Contract create(Agent agent, Customer customerLandlordId, Customer customerTenantId,
		LocalDate startedAt, LocalDate expiredAt, String url) {
		return new Contract(agent, customerLandlordId, customerTenantId, startedAt, expiredAt, url);
	}
}
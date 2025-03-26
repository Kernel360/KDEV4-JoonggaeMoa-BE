package org.silsagusi.joonggaemoa.domain.contract.entity;

import org.silsagusi.joonggaemoa.domain.agent.entity.AgentCustomer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
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
	private AgentCustomer agentLandlord;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_tenant")
	private AgentCustomer agentTenant;

}

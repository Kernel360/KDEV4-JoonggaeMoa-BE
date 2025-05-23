package org.silsagusi.core.domain.agent;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.core.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
@Table(
	name = "agents",
	uniqueConstraints = {
		@UniqueConstraint(name = "UK_agent_username", columnNames = {"username", "deleted_at"}),
		@UniqueConstraint(name = "UK_agent_phone", columnNames = {"phone", "deleted_at"}),
		@UniqueConstraint(name = "UK_agent_email", columnNames = {"email", "deleted_at"})
	})
@Getter
public class Agent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_id")
	private Long id;

	private String name;

	private String phone;

	private String email;

	private String username;

	private String password;

	private String office;

	private String region;

	@Column(name = "business_no")
	private String businessNo;

	@Enumerated(EnumType.STRING)
	private Role role;

	private Agent(String name, String phone, String email, String username, String password, String office,
		String region, String businessNo) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.office = office;
		this.region = region;
		this.businessNo = businessNo;
		this.role = Role.ROLE_AGENT;
	}

	public static Agent create(String name, String phone, String email, String username, String password, String office,
		String region, String businessNo) {
		return new Agent(name, phone, email, username, password, office, region, businessNo);
	}

	public void updateAgent(String name, String phone, String email, String username, String office, String region,
		String businessNo) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.office = office;
		this.region = region;
		this.businessNo = businessNo;
	}

	enum Role {
		ROLE_AGENT, ROLE_CUSTOMER
	}
}

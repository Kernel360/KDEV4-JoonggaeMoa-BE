package org.silsagusi.joonggaemoa.domain.agent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "agents")
@Getter
public class Agent {

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

	private String businessNo;

	private Role role;

	enum Role {
		ROLE_AGENT, ROLE_CUSTOMER
	}

	public Agent(String name, String phone, String email, String username, String password, String office,
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
}

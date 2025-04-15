package org.silsagusi.joonggaemoa.domain.agent.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.joonggaemoa.domain.customer.domain.Customer;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity(name = "agents")
@Getter
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_id")
	private Long id;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Customer> customers;

	private String name;

	private String phone;

	private String email;

	private String username;

	private String password;

	private String office;

	private String region;

	private String businessNo;

	@Enumerated(EnumType.STRING)
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

}

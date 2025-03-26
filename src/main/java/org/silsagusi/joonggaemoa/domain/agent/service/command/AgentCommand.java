package org.silsagusi.joonggaemoa.domain.agent.service.command;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentCommand {

	private Long id;
	private String name;
	private String phone;
	private String email;
	private String username;
	private String password;
	private String office;
	private String region;
	private String businessNo;
	private String role;

	public static AgentCommand of(Agent agent) {
		return AgentCommand.builder()
			.id(agent.getId())
			.name(agent.getName())
			.phone(agent.getPhone())
			.email(agent.getEmail())
			.username(agent.getUsername())
			.password(agent.getPassword())
			.office(agent.getOffice())
			.region(agent.getRegion())
			.businessNo(agent.getBusinessNo())
			.role(agent.getRole() + "")
			.build();
	}
}

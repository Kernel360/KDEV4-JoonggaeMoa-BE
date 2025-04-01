package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AgentDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String username;
		private String password;
		private String name;
		private String phone;
		private String email;
		private String office;
		private String region;
		private String businessNo;
	}

	@Getter
	@Builder
	public static class Response {
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

		public static Response of(AgentCommand agentCommand) {
			return AgentDto.Response.builder()
				.id(agentCommand.getId())
				.name(agentCommand.getName())
				.phone(agentCommand.getPhone())
				.email(agentCommand.getEmail())
				.username(agentCommand.getUsername())
				.password(agentCommand.getPassword())
				.office(agentCommand.getOffice())
				.region(agentCommand.getRegion())
				.businessNo(agentCommand.getBusinessNo())
				.role(agentCommand.getRole() + "")
				.build();
		}
	}
}

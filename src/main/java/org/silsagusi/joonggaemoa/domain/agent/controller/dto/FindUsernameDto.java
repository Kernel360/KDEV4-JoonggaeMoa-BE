package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import org.silsagusi.joonggaemoa.domain.agent.service.command.AgentCommand;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FindUsernameDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		@NotBlank
		private String name;

		@NotBlank
		private String phone;
	}

	@Getter
	@AllArgsConstructor
	public static class Response {
		private String username;

		public static Response of(AgentCommand agentCommand) {
			return new Response(agentCommand.getUsername());
		}
	}
}

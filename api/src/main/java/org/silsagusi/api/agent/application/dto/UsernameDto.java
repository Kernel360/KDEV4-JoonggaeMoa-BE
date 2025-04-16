package org.silsagusi.api.agent.application.dto;

import org.silsagusi.core.domain.agent.Agent;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UsernameDto {

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

		public static Response of(Agent agent) {
			return new Response(agent.getUsername());
		}
	}
}

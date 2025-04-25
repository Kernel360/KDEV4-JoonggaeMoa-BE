package org.silsagusi.api.agent.application.dto;

import org.silsagusi.core.domain.agent.Agent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsernameDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Schema(name = "UsernameRequestDto")
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
	}

	public static Response toResponse(Agent agent) {
		return new UsernameDto.Response(agent.getUsername());
	}
}

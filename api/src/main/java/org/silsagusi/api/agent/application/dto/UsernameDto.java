package org.silsagusi.api.agent.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}

package org.silsagusi.api.agent.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgentLoginRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;
}

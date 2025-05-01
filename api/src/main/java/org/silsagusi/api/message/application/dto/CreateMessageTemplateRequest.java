package org.silsagusi.api.message.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMessageTemplateRequest {
	@NotBlank
	private String title;

	@NotBlank
	private String content;
}

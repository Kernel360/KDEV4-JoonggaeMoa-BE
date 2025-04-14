package org.silsagusi.joonggaemoa.domain.message.service.dto;

import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MessageTemplateDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		@NotBlank
		private String title;

		@NotBlank
		private String content;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private String title;
		private String content;

		public static Response of(MessageTemplate messageTemplate) {
			return Response.builder()
				.id(messageTemplate.getId())
				.title(messageTemplate.getTitle())
				.content(messageTemplate.getContent())
				.build();
		}
	}
}

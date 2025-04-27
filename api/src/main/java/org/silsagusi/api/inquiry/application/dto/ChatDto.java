package org.silsagusi.api.inquiry.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String question;
	}

	@Builder
	@Getter
	public static class Response {
		private String answer;

		public static Response of(String answer) {
			return Response.builder().answer(answer).build();
		}
	}

}

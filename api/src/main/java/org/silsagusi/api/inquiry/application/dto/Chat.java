package org.silsagusi.api.inquiry.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Chat {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String question;
	}

	@Getter
	@AllArgsConstructor
	public static class Response {
		private String answer;
	}
}

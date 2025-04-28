package org.silsagusi.api.inquiry.infrastructure.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GeminiDto {

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		@JsonProperty("contents")
		private List<Content> contents;

		@Getter
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Content {

			@JsonProperty("parts")
			private List<Part> parts;

			@Getter
			@Builder
			@NoArgsConstructor
			@AllArgsConstructor
			public static class Part {
				@JsonProperty("text")
				private String text;
			}
		}
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private List<Candidate> candidates;

		@Getter
		@NoArgsConstructor
		@AllArgsConstructor
		public static class Candidate {
			private Content content;

			@Getter
			@NoArgsConstructor
			@AllArgsConstructor
			public static class Content {
				private List<Part> parts;

				@Getter
				@NoArgsConstructor
				@AllArgsConstructor
				public static class Part {
					private String text;
				}
			}
		}
	}
}

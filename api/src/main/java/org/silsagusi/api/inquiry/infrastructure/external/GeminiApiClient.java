package org.silsagusi.api.inquiry.infrastructure.external;

import java.util.List;

import org.apache.http.HttpHeaders;
import org.silsagusi.api.inquiry.application.dto.ChatDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeminiApiClient {

	private final WebClient geminiWebClient;
	private final String prefix = "대한민국의공인중개사 역할로 다음 질문에 대해 답변을 한국어 2문장 이내(100자 이내)로 요약해서 설명해줘.\n질문: ";

	@Value("${gemini.api.key}")
	private String apiKey;

	public Mono<ChatDto.Response> askGemini(ChatDto.Request chatRequest) {

		GeminiDto.Request request = GeminiDto.Request.builder()
			.contents(List.of(
				GeminiDto.Request.Content.builder()
					.parts(List.of(
						GeminiDto.Request.Content.Part.builder()
							.text(prefix + chatRequest.getQuestion())
							.build()
					))
					.build()
			))
			.build();

		return geminiWebClient.post()
			.uri("/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.bodyValue(request)
			.retrieve()
			.bodyToMono(GeminiDto.Response.class)
			.map(response -> {
				String answer = "답변을 생성할 수 없습니다.";
				if (response.getCandidates() != null && !response.getCandidates().isEmpty()) {
					answer = response.getCandidates().get(0).getContent().getParts().get(0).getText();
				}
				return ChatDto.Response.builder()
					.answer(answer)
					.build();
			});
	}

}

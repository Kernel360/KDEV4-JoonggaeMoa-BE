package org.silsagusi.api.inquiry.infrastructure.external;

import java.util.List;

import org.apache.http.HttpHeaders;
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

	public String askGemini(String question) {

		GeminiDto.Request request = GeminiDto.Request.builder()
			.contents(List.of(
				GeminiDto.Request.Content.builder()
					.parts(List.of(
						GeminiDto.Request.Content.Part.builder()
							.text(prefix + question)
							.build()
					))
					.build()
			))
			.build();

		GeminiDto.Response geminiResponse = geminiWebClient.post()
			.uri("/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.bodyValue(request)
			.retrieve()
			.bodyToMono(GeminiDto.Response.class)
			.onErrorResume(e -> {
				log.error("Gemini 호출 실패", e);
				return Mono.empty();
			})
			.block();

		try {
			return geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
		} catch (Exception e) {
			log.error("Gemini 답변 parsing 실패", e);
			return "답변을 생성할 수 없습니다";
		}

	}

}

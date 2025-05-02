package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquiryDetailResponse {
	private Long id;
	private String name;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private List<InquiryAnswerResponse> answers;

	@Getter
	@Builder
	public static class InquiryAnswerResponse {
		private Long agentId;
		private String agentName;
		private String agentOffice;
		private String agentRegion;
		private String content;
		private LocalDateTime createdAt;

		public static List<InquiryAnswerResponse> toResponses(List<InquiryAnswer> answers) {
			return answers.stream()
				.map(answer -> InquiryAnswerResponse.builder()
					.agentId(answer.getAgent().getId())
					.agentName(answer.getAgent().getName())
					.agentOffice(answer.getAgent().getOffice())
					.agentRegion(answer.getAgent().getRegion())
					.content(answer.getContent())
					.createdAt(answer.getCreatedAt())
					.build()
				).toList();
		}
	}

	public static InquiryDetailResponse toResponse(Inquiry inquiry) {
		return InquiryDetailResponse.builder()
			.id(inquiry.getId())
			.name(inquiry.getName())
			.title(inquiry.getTitle())
			.content(inquiry.getContent())
			.createdAt(inquiry.getCreatedAt())
			.answers(InquiryAnswerResponse.toResponses(inquiry.getAnswers()))
			.build();
	}
}

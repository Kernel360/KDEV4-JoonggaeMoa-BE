package org.silsagusi.api.inquiry.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InquiryAnswerResponse {
	private Long agentId;
	private String agentName;
	private String agentOffice;
	private String agentRegion;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static InquiryAnswerResponse toResponse(InquiryAnswer inquiryAnswer) {
		return InquiryAnswerResponse.builder()
			.agentId(inquiryAnswer.getAgent().getId())
			.agentName(inquiryAnswer.getAgent().getName())
			.agentOffice(inquiryAnswer.getAgent().getOffice())
			.agentRegion(inquiryAnswer.getAgent().getRegion())
			.content(inquiryAnswer.getContent())
			.createdAt(inquiryAnswer.getCreatedAt())
			.updatedAt(inquiryAnswer.getUpdatedAt())
			.build();
	}
}

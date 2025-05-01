package org.silsagusi.api.message.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.message.entity.Message;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageResponse {
	private Long id;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String content;
	private LocalDateTime createdAt;
	private String sendStatus;
	private LocalDateTime sendAt;

	public static MessageResponse toResponse(Message message) {
		return MessageResponse.builder()
			.id(message.getId())
			.customerId(message.getCustomer().getId())
			.customerName(message.getCustomer().getName())
			.customerPhone(message.getCustomer().getPhone())
			.content(message.getContent())
			.createdAt(message.getCreatedAt())
			.sendStatus(message.getSendStatus() + "")
			.sendAt(message.getSendAt())
			.build();
	}
}

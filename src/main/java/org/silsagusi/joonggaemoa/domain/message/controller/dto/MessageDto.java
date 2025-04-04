package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;

import lombok.Builder;
import lombok.Getter;

public class MessageDto {

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private Long customerId;
		private String customerName;
		private String customerPhone;
		private String content;
		private String createdAt;
		private String sendStatus;

		public static Response of(MessageCommand command) {
			return Response.builder()
				.id(command.getId())
				.customerId(command.getCustomerId())
				.customerName(command.getCustomerName())
				.customerPhone(command.getCustomerPhone())
				.content(command.getContent())
				.createdAt(command.getCreatedAt())
				.sendStatus(command.getSendStatus())
				.build();
		}
	}
}

package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservedMessageDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {
		private String content;
		private String sendAt;
		private List<Long> customerIdList;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private LocalDateTime sendAt;
		private Long customerId;
		private String customerName;
		private String customerPhone;
		private String content;

		public static Response of(ReservedMessageCommand command) {
			return Response.builder()
				.id(command.getId())
				.sendAt(command.getSendAt())
				.customerId(command.getCustomerId())
				.customerName(command.getCustomerName())
				.customerPhone(command.getCustomerPhone())
				.content(command.getContent())
				.build();
		}
	}
}

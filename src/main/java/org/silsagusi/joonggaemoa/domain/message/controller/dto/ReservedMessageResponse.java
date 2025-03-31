package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedMessageResponse {

	private Long id;
	private LocalDateTime sendAt;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String content;

	public static ReservedMessageResponse of(ReservedMessageCommand command) {
		return ReservedMessageResponse.builder()
			.id(command.getId())
			.sendAt(command.getSendAt())
			.customerId(command.getCustomerId())
			.customerName(command.getCustomerName())
			.customerPhone(command.getCustomerPhone())
			.content(command.getContent())
			.build();
	}

}

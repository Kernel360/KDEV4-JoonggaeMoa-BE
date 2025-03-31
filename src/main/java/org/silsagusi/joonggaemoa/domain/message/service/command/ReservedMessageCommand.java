package org.silsagusi.joonggaemoa.domain.message.service.command;

import java.time.LocalDateTime;

import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservedMessageCommand {

	private Long id;
	private LocalDateTime sendAt;
	private Long customerId;
	private String customerName;
	private String customerPhone;
	private String content;

	public static ReservedMessageCommand of(ReservedMessage reservedMessage) {
		return ReservedMessageCommand.builder()
			.id(reservedMessage.getId())
			.sendAt(reservedMessage.getSendAt())
			.customerId(reservedMessage.getCustomer().getId())
			.customerName(reservedMessage.getCustomer().getName())
			.customerPhone(reservedMessage.getCustomer().getPhone())
			.content(reservedMessage.getContent())
			.build();
	}
}

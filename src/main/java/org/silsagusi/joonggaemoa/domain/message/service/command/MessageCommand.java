package org.silsagusi.joonggaemoa.domain.message.service.command;

import org.silsagusi.joonggaemoa.domain.message.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageCommand {

	private Long id;
	private Long customerId;
	private String customerName;
	private String content;
	private String createdAt;
	private String sendStatus;

	public static MessageCommand of(Message message) {
		return MessageCommand.builder()
			.id(message.getId())
			.customerId(message.getCustomer().getId())
			.customerName(message.getCustomer().getName())
			.content(message.getContent())
			.createdAt(message.getCreatedAt())
			.sendStatus(String.valueOf(message.getSendStatus()))
			.build();
	}
}

package org.silsagusi.joonggaemoa.domain.message.service.command;

import java.time.LocalDateTime;

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
	private String customerPhone;
	private String content;
	private String createdAt;
	private String sendStatus;
	private LocalDateTime sendAt;

	public static MessageCommand of(Message message) {
		return MessageCommand.builder()
			.id(message.getId())
			.customerId(message.getCustomer().getId())
			.customerName(message.getCustomer().getName())
			.customerPhone(message.getCustomer().getPhone())
			.content(message.getContent())
			.createdAt(message.getCreatedAt())
			.sendStatus(String.valueOf(message.getSendStatus()))
			.sendAt(message.getSendAt())
			.build();
	}
}

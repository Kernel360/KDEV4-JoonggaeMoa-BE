package org.silsagusi.api.message.application.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.message.application.dto.UpdateMessageRequest;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.message.command.UpdateMessageCommand;
import org.silsagusi.core.domain.message.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

	public List<Message> toEntityList(List<Customer> customerList, String content, LocalDateTime sendAt) {
		return customerList.stream()
			.map(customer ->
				Message.create(customer, convertContent(content, customer.getName()), sendAt)
			).toList();
	}

	private String convertContent(String content, String customerName) {
		if (content == null || customerName == null)
			return content;
		return content.replace("${이름}", customerName);
	}

	public UpdateMessageCommand toUpdateMessageCommand(UpdateMessageRequest updateMessageRequest) {
		return UpdateMessageCommand.builder()
			.content(updateMessageRequest.getContent())
			.sendAt(updateMessageRequest.getSendAt())
			.build();
	}
}

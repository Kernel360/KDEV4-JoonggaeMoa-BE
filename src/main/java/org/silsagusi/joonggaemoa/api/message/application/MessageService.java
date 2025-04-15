package org.silsagusi.joonggaemoa.api.message.application;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.domain.AgentDataProvider;
import org.silsagusi.joonggaemoa.api.customer.domain.dataProvider.CustomerDataProvider;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.silsagusi.joonggaemoa.api.message.application.dto.MessageDto;
import org.silsagusi.joonggaemoa.api.message.application.dto.MessageUpdateRequest;
import org.silsagusi.joonggaemoa.api.message.domain.dataProvider.MessageDataProvider;
import org.silsagusi.joonggaemoa.api.message.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

	private final AgentDataProvider agentDataProvider;
	private final MessageDataProvider messageDataProvider;
	private final CustomerDataProvider customerDataProvider;

	public Page<MessageDto.Response> getMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageDataProvider.getMessagePageByAgent(agentId, pageable);
		return messagePage.map(MessageDto.Response::of);
	}

	public void createMessage(MessageDto.Request messageRequest) {
		String content = messageRequest.getContent();
		List<Customer> customerList = customerDataProvider.getCustomerListByIdList(messageRequest.getCustomerIdList());
		LocalDateTime sendAt = messageRequest.getSendAt();

		customerList.forEach(customer -> {
			Message message = new Message(
				customer,
				messageDataProvider.convertContent(content, customer.getName()),
				sendAt
			);
			messageDataProvider.createMessage(message);
		});
	}

	public void updateMessage(Long agentId, Long messageId, MessageUpdateRequest messageUpdateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Message message = messageDataProvider.getMessage(messageId);
		messageDataProvider.validateMessageWithAgent(message, agent);
		messageDataProvider.validateMessageStatusEqualsPending(message);
		message.updateMessage(messageUpdateRequest.getSendAt(), messageUpdateRequest.getContent());
		messageDataProvider.updateMessage(message);
	}

	public Page<MessageDto.Response> getReservedMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageDataProvider.getReservedMessagePageByAgent(agentId, pageable);
		return messagePage.map(MessageDto.Response::of);
	}

	public MessageDto.Response getReservedMessage(Long messageId) {
		Message message = messageDataProvider.getMessage(messageId);
		return MessageDto.Response.of(message);
	}

	public void deleteReservedMessage(Long agentId, Long messageId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Message message = messageDataProvider.getMessage(messageId);
		messageDataProvider.validateMessageWithAgent(message, agent);
		messageDataProvider.validateMessageStatusEqualsPending(message);
		messageDataProvider.deleteMessage(message);
	}
}

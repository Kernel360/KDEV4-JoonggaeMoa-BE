package org.silsagusi.api.message.application;

import java.util.List;

import org.silsagusi.api.agent.infrastructure.AgentDataProvider;
import org.silsagusi.api.customer.infrastructure.CustomerDataProvider;
import org.silsagusi.api.message.application.dto.MessageDto;
import org.silsagusi.api.message.application.dto.UpdateMessageRequest;
import org.silsagusi.api.message.infrastructure.dataProvider.MessageDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.message.command.UpdateMessageCommand;
import org.silsagusi.core.domain.message.entity.Message;
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
	private final MessageMapper messageMapper;

	public Page<MessageDto.Response> getMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageDataProvider.getMessagePageByAgent(agentId, pageable);
		return messagePage.map(MessageDto.Response::of);
	}

	public void createMessages(MessageDto.Request messageRequest) {
		List<Customer> customerList = customerDataProvider.getCustomerListByIdList(messageRequest.getCustomerIdList());

		List<Message> messages = messageMapper.toEntityList(customerList, messageRequest.getContent(),
			messageRequest.getSendAt());

		messageDataProvider.createMessages(messages);
	}

	public void updateMessage(Long agentId, Long messageId, UpdateMessageRequest updateMessageRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Message message = messageDataProvider.getMessage(messageId);

		messageDataProvider.validateMessageWithAgent(message, agent);
		messageDataProvider.validateMessageStatusEqualsPending(message);

		UpdateMessageCommand updateMessageCommand = messageMapper.toUpdateMessageCommand(updateMessageRequest);
		messageDataProvider.updateMessage(message, updateMessageCommand);
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

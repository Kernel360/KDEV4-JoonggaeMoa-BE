package org.silsagusi.joonggaemoa.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.silsagusi.joonggaemoa.domain.message.entity.SendStatus;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final CustomerRepository customerRepository;
	private final MessageRepository messageRepository;
	private final AgentRepository agentRepository;

	public Page<MessageCommand> getMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageRepository.findAllByCustomerAgent_Id(agentId, pageable);

		return messagePage.map(MessageCommand::of);
	}

	public void createMessage(String content, LocalDateTime sendAt, List<Long> customerIdList) {
		customerIdList.stream()
			.map(id -> customerRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER)))
			.map(customer -> new Message(customer, content, sendAt))
			.forEach(messageRepository::save);
	}

	public void updateMessage(Long agentId, Long messageId, LocalDateTime sendAt, String content) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!agent.equals(message.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		if (message.getSendStatus() != SendStatus.PENDING) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}

		message.updateMessage(sendAt, content);

		messageRepository.save(message);
	}

	public Page<MessageCommand> getReservedMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageRepository.findAllByCustomerAgent_IdAndSendStatus(agentId,
			SendStatus.PENDING, pageable);
		return messagePage.map(MessageCommand::of);
	}

	public MessageCommand getReservedMessage(Long messageId) {
		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return MessageCommand.of(message);
	}

	public void deleteReservedMessage(Long agentId, Long messageId) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!agent.equals(message.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		if (message.getSendStatus() != SendStatus.PENDING) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}

		messageRepository.delete(message);
	}
}

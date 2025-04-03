package org.silsagusi.joonggaemoa.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
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
	private final ReservedMessageRepository reservedMessageRepository;
	private final AgentRepository agentRepository;

	public Page<MessageCommand> getMessagePage(Pageable pageable) {

		Page<Message> messagePage = messageRepository.findAll(pageable);

		return messagePage.map(MessageCommand::of);
	}

	public void createReservedMessage(String content, LocalDateTime sendAt, List<Long> customerIdList) {
		customerIdList.stream()
			.map(id -> customerRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER)))
			.map(customer -> new ReservedMessage(customer, sendAt, content))
			.forEach(reservedMessageRepository::save);
	}

	public void updateReservedMessage(Long agentId, Long reservedMessageId, LocalDateTime sendAt,
		String content) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		ReservedMessage reservedMessage = reservedMessageRepository.findById(reservedMessageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!agent.equals(reservedMessage.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		reservedMessage.updateReservedMessage(sendAt, content);

		reservedMessageRepository.save(reservedMessage);
	}

	public Page<ReservedMessageCommand> getReservedMessagePage(Pageable pageable) {
		Page<ReservedMessage> reservedMessagePage = reservedMessageRepository.findAll(pageable);
		return reservedMessagePage.map(ReservedMessageCommand::of);
	}

	public ReservedMessageCommand getReservedMessage(Long reservedMessageId) {
		ReservedMessage reservedMessage = reservedMessageRepository.findById(reservedMessageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return ReservedMessageCommand.of(reservedMessage);

	}

	public void deleteReservedMessage(Long agentId, Long reservedMessageId) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		ReservedMessage reservedMessage = reservedMessageRepository.findById(reservedMessageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!agent.equals(reservedMessage.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}

		reservedMessageRepository.delete(reservedMessage);
	}
}

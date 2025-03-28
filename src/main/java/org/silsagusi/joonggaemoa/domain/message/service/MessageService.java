package org.silsagusi.joonggaemoa.domain.message.service;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final AgentRepository agentRepository;
	private final CustomerRepository customerRepository;
	private final MessageRepository messageRepository;
	private final ReservedMessageRepository reservedMessageRepository;

	public List<MessageCommand> getMessage(Long agentId, Long lastMessageId) {
		List<Message> messages;

		if (lastMessageId == null || lastMessageId == 0) {
			// 초기 요청: 가장 최신 메시지 10개 조회
			messages = messageRepository.findTop10ByCustomerAgent_IdOrderByIdDesc(agentId);
		} else {
			// lastMessageId 이후 메시지 10개 조회
			messages = messageRepository.findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(agentId, lastMessageId);
		}

		return messages.stream()
			.map(MessageCommand::of)
			.toList();
	}

	public void reserveMessage(String content, String sendAt, List<Long> customerIdList) {
		customerIdList.stream()
			.map(id -> customerRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT)))
			.map(customer -> new ReservedMessage(customer, LocalDateTime.parse(sendAt), content))
			.forEach(reservedMessageRepository::save);
	}
}

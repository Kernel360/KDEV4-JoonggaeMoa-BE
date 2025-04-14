package org.silsagusi.joonggaemoa.domain.message.service;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.silsagusi.joonggaemoa.domain.message.entity.SendStatus;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.dto.MessageDto;
import org.silsagusi.joonggaemoa.domain.message.service.dto.MessageUpdateRequest;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

	private final CustomerRepository customerRepository;
	private final MessageRepository messageRepository;
	private final AgentRepository agentRepository;

	public Page<MessageDto.Response> getMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageRepository.findAllByCustomer_Agent_Id(agentId, pageable);

		messagePage.forEach(message -> {
			log.info("메세지 대상의 이름 : {}", message.getCustomer().getName());
		});

		return messagePage.map(MessageDto.Response::of);
	}

	public void createMessage(MessageDto.Request messageRequest) {
		messageRequest.getCustomerIdList().stream()
			.map(id -> customerRepository.findById(id)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CUSTOMER)))
			.map(customer -> new Message(customer, convertContent(messageRequest.getContent(), customer.getName()),
				messageRequest.getSendAt()))
			.forEach(messageRepository::save);
	}

	private String convertContent(String content, String customerName) {
		if (content == null || customerName == null)
			return content;
		return content.replace("${이름}", customerName);
	}

	public void updateMessage(Long agentId, Long messageId, MessageUpdateRequest messageUpdateRequest) {
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

		message.updateMessage(messageUpdateRequest.getSendAt(), messageUpdateRequest.getContent());

		messageRepository.save(message);
	}

	public Page<MessageDto.Response> getReservedMessagePage(Long agentId, Pageable pageable) {
		Page<Message> messagePage = messageRepository.findAllByCustomer_Agent_IdAndSendStatus(agentId,
			SendStatus.PENDING, pageable);

		log.info("Agent ID: {}, SendStatus: {}, Page: {}", agentId, pageable, messagePage);
		log.info("Retrieved message count: {}", messagePage.getTotalElements());
		log.info("Page number: {}, Page size: {}, Total pages: {}",
			messagePage.getNumber(), messagePage.getSize(), messagePage.getTotalPages());
		messagePage.forEach(message -> {
			log.info("메세지 대상의 이름 : {}", message.getCustomer().getName());
		});

		return messagePage.map(MessageDto.Response::of);
	}

	public MessageDto.Response getReservedMessage(Long messageId) {
		Message message = messageRepository.findById(messageId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return MessageDto.Response.of(message);
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

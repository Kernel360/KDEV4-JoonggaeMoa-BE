package org.silsagusi.api.message.infrastructure.dataProvider;

import org.silsagusi.api.agent.infrastructure.AgentRepository;
import org.silsagusi.api.message.infrastructure.repository.MessageRepository;
import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.SendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageDataProvider {

	private final AgentRepository agentRepository;
	private final MessageRepository messageRepository;

	public void createMessage(Message message) {
		messageRepository.save(message);
	}

	public Message getMessage(Long id) {
		return messageRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public Page<Message> getMessagePageByAgent(Long agentId, Pageable pageable) {
		return messageRepository.findAllByCustomer_Agent_Id(agentId, pageable);
	}

	public Page<Message> getReservedMessagePageByAgent(Long agentId, Pageable pageable) {
		return messageRepository.findAllByCustomer_Agent_IdAndSendStatus(agentId,
			SendStatus.PENDING, pageable);
	}

	public void updateMessage(Message message) {
		messageRepository.save(message);
	}

	public void deleteMessage(Message message) {
		messageRepository.delete(message);
	}

	public String convertContent(String content, String customerName) {
		if (content == null || customerName == null)
			return content;
		return content.replace("${이름}", customerName);
	}

	public void validateMessageWithAgent(Message message, Agent agent) {
		if (!agent.equals(message.getCustomer().getAgent())) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
	}

	public void validateMessageStatusEqualsPending(Message message) {
		if (message.getSendStatus() != SendStatus.PENDING) {
			throw new CustomException(ErrorCode.BAD_REQUEST);
		}
	}
}

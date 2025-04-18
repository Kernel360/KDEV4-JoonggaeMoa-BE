package org.silsagusi.api.message.infrastructure.dataProvider;

import java.util.List;

import org.silsagusi.api.message.infrastructure.repository.MessageRepository;
import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.message.command.UpdateMessageCommand;
import org.silsagusi.core.domain.message.entity.Message;
import org.silsagusi.core.domain.message.entity.SendStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageDataProvider {

	private final MessageRepository messageRepository;

	public void createMessages(List<Message> messages) {
		messageRepository.saveAll(messages);
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

	public void updateMessage(Message message, UpdateMessageCommand updateMessageCommand) {
		message.updateMessage(updateMessageCommand.getSendAt(), updateMessageCommand.getContent());
		messageRepository.save(message);
	}

	public void deleteMessage(Message message) {
		messageRepository.delete(message);
	}

}

package org.silsagusi.joonggaemoa.api.message.domain.dataProvider;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.message.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageDataProvider {

	void createMessage(Message message);

	Message getMessage(Long id);

	Page<Message> getMessagePageByAgent(Long agentId, Pageable pageable);

	Page<Message> getReservedMessagePageByAgent(Long agentId, Pageable pageable);

	void updateMessage(Message message);

	void deleteMessage(Message message);

	String convertContent(String content, String customerName);

	void validateMessageWithAgent(Message message, Agent agent);

	void validateMessageStatusEqualsPending(Message message);
}

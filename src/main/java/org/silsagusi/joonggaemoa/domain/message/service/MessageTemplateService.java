package org.silsagusi.joonggaemoa.domain.message.service;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Category;
import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageTemplateRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageTemplateCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

	private final AgentRepository agentRepository;
	private final MessageTemplateRepository messageTemplateRepository;

	public void createMessageTemplate(Agent agent) {

		MessageTemplate messageTemplate1 = MessageTemplate.create(agent, Category.BIRTHDAY, "");
		MessageTemplate messageTemplate2 = MessageTemplate.create(agent, Category.EXPIRATION, "");
		MessageTemplate messageTemplate3 = MessageTemplate.create(agent, Category.WELCOME, "");

		messageTemplateRepository.save(messageTemplate1);
		messageTemplateRepository.save(messageTemplate2);
		messageTemplateRepository.save(messageTemplate3);
	}

	public MessageTemplateCommand getMessageTemplate(Long agentId, String category) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		MessageTemplate messageTemplate = messageTemplateRepository.findByAgentAndCategory(agent,
				Category.valueOf(category))
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		return MessageTemplateCommand.of(messageTemplate);
	}

	public MessageTemplateCommand updateMessageTemplate(Long agentId, String category, String content) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		MessageTemplate messageTemplate = messageTemplateRepository.findByAgentAndCategory(agent,
				Category.valueOf(category))
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		messageTemplate.setContent(content);
		MessageTemplate savedMessageTemplate = messageTemplateRepository.save(messageTemplate);

		return MessageTemplateCommand.of(savedMessageTemplate);
	}

	public void deleteMessageTemplate(Long agentId, String category) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		MessageTemplate messageTemplate = messageTemplateRepository.findByAgentAndCategory(agent,
				Category.valueOf(category))
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		messageTemplate.setContent("");

		messageTemplateRepository.save(messageTemplate);
	}
}

package org.silsagusi.api.message.infrastructure.dataProvider;

import java.util.List;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;
import org.silsagusi.api.message.infrastructure.repository.MessageTemplateRepository;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageTemplateDataProvider {

	private final MessageTemplateRepository messageTemplateRepository;

	public void createDefaultMessageTemplate(Agent agent) {
		MessageTemplate messageTemplate1 = MessageTemplate.create(agent, "생일 축하", "생일 축하드립니다");
		MessageTemplate messageTemplate2 = MessageTemplate.create(agent, "계약 기한 만료", "곧 계약 기한 만료입니다");
		MessageTemplate messageTemplate3 = MessageTemplate.create(agent, "가입 환영", "가입을 환영합니다");

		messageTemplateRepository.save(messageTemplate1);
		messageTemplateRepository.save(messageTemplate2);
		messageTemplateRepository.save(messageTemplate3);
	}

	public void createMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.save(messageTemplate);
	}

	public List<MessageTemplate> getMessageTemplateList(Agent agent) {
		return messageTemplateRepository.findByAgent(agent);
	}

	public MessageTemplate getMessageTemplateById(Long templateId) {
		return messageTemplateRepository.findById(templateId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public void updateMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.save(messageTemplate);
	}

	public void deleteMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.delete(messageTemplate);
	}

	public void validateMessageTemplateWithAgent(MessageTemplate messageTemplate, Agent agent) {
		if (!messageTemplate.getAgent().equals(agent)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}

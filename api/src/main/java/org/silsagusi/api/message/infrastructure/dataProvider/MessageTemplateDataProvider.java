package org.silsagusi.api.message.infrastructure.dataProvider;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
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
		List<MessageTemplate> messageTemplates = new ArrayList<>();
		messageTemplates.add(MessageTemplate.create(agent, "생일 축하", "생일 축하드립니다"));
		messageTemplates.add(MessageTemplate.create(agent, "계약 기한 만료", "곧 계약 기한 만료입니다"));
		messageTemplates.add(MessageTemplate.create(agent, "가입 환영", "가입을 환영합니다"));

		messageTemplateRepository.saveAll(messageTemplates);
	}

	public void createMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.save(messageTemplate);
	}

	public List<MessageTemplate> getMessageTemplateList(Agent agent) {
		return messageTemplateRepository.findByAgentAndDeletedAtIsNull(agent);
	}

	public MessageTemplate getMessageTemplateById(Long templateId) {
		return messageTemplateRepository.findByIdAndDeletedAtIsNull(templateId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public void updateMessageTemplate(String title, String content, MessageTemplate messageTemplate) {
		messageTemplate.updateMessageTemplate(title, content);
	}

	public void deleteMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplate.markAsDeleted();
	}
}
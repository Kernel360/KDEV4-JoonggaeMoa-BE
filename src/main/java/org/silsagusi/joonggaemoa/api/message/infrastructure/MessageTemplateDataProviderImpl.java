package org.silsagusi.joonggaemoa.api.message.infrastructure;

import java.util.List;

import org.silsagusi.joonggaemoa.core.customResponse.exception.CustomException;
import org.silsagusi.joonggaemoa.core.customResponse.exception.ErrorCode;
import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.message.dataProvider.MessageTemplateDataProvider;
import org.silsagusi.joonggaemoa.core.domain.message.entity.MessageTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageTemplateDataProviderImpl implements MessageTemplateDataProvider {

	private final MessageTemplateRepository messageTemplateRepository;

	@Override
	public void createDefaultMessageTemplate(Agent agent) {
		MessageTemplate messageTemplate1 = MessageTemplate.create(agent, "생일 축하", "생일 축하드립니다");
		MessageTemplate messageTemplate2 = MessageTemplate.create(agent, "계약 기한 만료", "곧 계약 기한 만료입니다");
		MessageTemplate messageTemplate3 = MessageTemplate.create(agent, "가입 환영", "가입을 환영합니다");

		messageTemplateRepository.save(messageTemplate1);
		messageTemplateRepository.save(messageTemplate2);
		messageTemplateRepository.save(messageTemplate3);
	}

	@Override
	public void createMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.save(messageTemplate);
	}

	@Override
	public List<MessageTemplate> getMessageTemplateList(Agent agent) {
		return messageTemplateRepository.findByAgent(agent);
	}

	@Override
	public MessageTemplate getMessageTemplateById(Long templateId) {
		return messageTemplateRepository.findById(templateId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	@Override
	public void updateMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.save(messageTemplate);
	}

	@Override
	public void deleteMessageTemplate(MessageTemplate messageTemplate) {
		messageTemplateRepository.delete(messageTemplate);
	}

	@Override
	public void validateMessageTemplateWithAgent(MessageTemplate messageTemplate, Agent agent) {
		if (!messageTemplate.getAgent().equals(agent)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}

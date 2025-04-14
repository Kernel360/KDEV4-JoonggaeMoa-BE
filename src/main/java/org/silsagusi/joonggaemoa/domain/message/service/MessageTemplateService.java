package org.silsagusi.joonggaemoa.domain.message.service;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.MessageTemplate;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageTemplateRepository;
import org.silsagusi.joonggaemoa.domain.message.service.dto.MessageTemplateDto;
import org.silsagusi.joonggaemoa.domain.message.service.dto.MessageTemplateUpdateRequest;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageTemplateService {

	private final AgentRepository agentRepository;
	private final MessageTemplateRepository messageTemplateRepository;

	public void createDefaultMessageTemplate(Agent agent) {

		MessageTemplate messageTemplate1 = MessageTemplate.create(agent, "생일 축하", "생일 축하드립니다");
		MessageTemplate messageTemplate2 = MessageTemplate.create(agent, "계약 기한 만료", "곧 계약 기한 만료입니다");
		MessageTemplate messageTemplate3 = MessageTemplate.create(agent, "가입 환영", "가입을 환영합니다");

		messageTemplateRepository.save(messageTemplate1);
		messageTemplateRepository.save(messageTemplate2);
		messageTemplateRepository.save(messageTemplate3);
	}

	public void createMessageTemplate(Long agentId, MessageTemplateDto.Request messageTemplateRequest) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		MessageTemplate messageTemplate = MessageTemplate.create(agent, messageTemplateRequest.getTitle(),
			messageTemplateRequest.getContent());

		messageTemplateRepository.save(messageTemplate);
	}

	public List<MessageTemplateDto.Response> getMessageTemplateList(Long agentId) {
		List<MessageTemplate> messageTemplateList = messageTemplateRepository.findByAgentId(agentId);

		return messageTemplateList.stream()
			.map(MessageTemplateDto.Response::of)
			.toList();
	}

	public void updateMessageTemplate(Long agentId, Long templateId, MessageTemplateUpdateRequest requestDto) {
		MessageTemplate messageTemplate = messageTemplateRepository.findById(templateId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!messageTemplate.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		messageTemplate.updateMessageTemplate(requestDto.getTitle(), requestDto.getContent());

		messageTemplateRepository.save(messageTemplate);
	}

	public void deleteMessageTemplate(Long agentId, Long templateId) {
		MessageTemplate messageTemplate = messageTemplateRepository.findById(templateId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!messageTemplate.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		messageTemplateRepository.delete(messageTemplate);
	}
}

package org.silsagusi.api.message.controller;

import java.util.List;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.message.application.dto.CreateMessageTemplateRequest;
import org.silsagusi.api.message.application.dto.MessageTemplateResponse;
import org.silsagusi.api.message.application.dto.UpdateMessageTemplateRequest;
import org.silsagusi.api.message.application.service.MessageTemplateService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageTemplateController {

	private final MessageTemplateService messageTemplateService;

	@GetMapping("/api/messages/templates")
	public ResponseEntity<ApiResponse<List<MessageTemplateResponse>>> getMessageTemplateList(
		@CurrentAgentId Long agentId
	) {
		List<MessageTemplateResponse> responseList = messageTemplateService.getMessageTemplates(agentId);
		return ResponseEntity.ok(ApiResponse.ok(responseList));
	}

	@PostMapping("/api/messages/templates")
	public ResponseEntity<ApiResponse<Void>> createMessageTemplate(
		@CurrentAgentId Long agentId,
		@RequestBody @Valid CreateMessageTemplateRequest messageTemplateRequest
	) {
		messageTemplateService.createMessageTemplate(agentId, messageTemplateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/messages/templates/{templateId}")
	public ResponseEntity<ApiResponse<Void>> updateMessageTemplate(
		@CurrentAgentId Long agentId,
		@PathVariable(name = "templateId") Long templateId,
		@RequestBody @Valid UpdateMessageTemplateRequest requestDto
	) {
		messageTemplateService.updateMessageTemplate(agentId, templateId, requestDto);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/messages/templates/{templateId}")
	public ResponseEntity<ApiResponse<Void>> deleteMessageTemplate(
		@CurrentAgentId Long agentId,
		@PathVariable(name = "templateId") Long templateId
	) {
		messageTemplateService.deleteMessageTemplate(agentId, templateId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}

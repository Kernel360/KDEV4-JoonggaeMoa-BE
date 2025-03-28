package org.silsagusi.joonggaemoa.domain.message.controller;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageTemplateRequest;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageTemplateResponse;
import org.silsagusi.joonggaemoa.domain.message.service.MessageTemplateService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageTemplateCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageTemplateController {

	private final MessageTemplateService messageTemplateService;

	@GetMapping("/api/agent/{agentId}/message/template")
	public ResponseEntity<ApiResponse<MessageTemplateResponse>> getMessageTemplate(
		@PathVariable Long agentId,
		@RequestParam String category
	) {
		MessageTemplateCommand command = messageTemplateService.getMessageTemplate(agentId, category);

		MessageTemplateResponse responseDto = MessageTemplateResponse.of(command);

		return ResponseEntity.ok(ApiResponse.ok(responseDto));
	}

	@PatchMapping("/api/agent/{agentId}/message/template")
	public ResponseEntity<ApiResponse<MessageTemplateResponse>> updateMessageTemplate(
		@PathVariable Long agentId,
		@RequestBody MessageTemplateRequest requestDto
	) {
		MessageTemplateCommand command = messageTemplateService.updateMessageTemplate(agentId,
			requestDto.getCategory(), requestDto.getContent());

		MessageTemplateResponse responseDto = MessageTemplateResponse.of(command);

		return ResponseEntity.ok(ApiResponse.ok(responseDto));
	}

	@DeleteMapping("/api/agent/{agentId}/message/template")
	public ResponseEntity<ApiResponse<Void>> deleteMessageTemplate(
		@PathVariable Long agentId,
		@RequestParam String category
	) {
		messageTemplateService.deleteMessageTemplate(agentId, category);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

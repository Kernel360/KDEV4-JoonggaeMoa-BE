package org.silsagusi.api.message.controller;

import java.util.List;

import org.silsagusi.api.message.application.dto.MessageTemplateDto;
import org.silsagusi.api.message.application.dto.MessageTemplateUpdateRequest;
import org.silsagusi.api.message.application.service.MessageTemplateService;
import org.silsagusi.core.customResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageTemplateController {

	private final MessageTemplateService messageTemplateService;

	@GetMapping("/api/messages/templates")
	public ResponseEntity<ApiResponse<List<MessageTemplateDto.Response>>> getMessageTemplateList(
		HttpServletRequest request
	) {
		List<MessageTemplateDto.Response> responseList = messageTemplateService.getMessageTemplates(
			(Long)request.getAttribute("agentId")
		);

		return ResponseEntity.ok(ApiResponse.ok(responseList));
	}

	@PostMapping("/api/messages/templates")
	public ResponseEntity<ApiResponse<Void>> createMessageTemplate(
		HttpServletRequest request,
		@RequestBody @Valid MessageTemplateDto.Request messageTemplateRequest
	) {
		messageTemplateService.createMessageTemplate(
			(Long)request.getAttribute("agentId"),
			messageTemplateRequest
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/messages/templates/{templateId}")
	public ResponseEntity<ApiResponse<Void>> updateMessageTemplate(
		HttpServletRequest request,
		@PathVariable(name = "templateId") Long templateId,
		@RequestBody @Valid MessageTemplateUpdateRequest requestDto
	) {
		messageTemplateService.updateMessageTemplate(
			(Long)request.getAttribute("agentId"),
			templateId,
			requestDto
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/messages/templates/{templateId}")
	public ResponseEntity<ApiResponse<Void>> deleteMessageTemplate(
		HttpServletRequest request,
		@PathVariable(name = "templateId") Long templateId
	) {
		messageTemplateService.deleteMessageTemplate(
			(Long)request.getAttribute("agentId"),
			templateId
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

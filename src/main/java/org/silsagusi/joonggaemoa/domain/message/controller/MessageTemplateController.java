package org.silsagusi.joonggaemoa.domain.message.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageTemplateDto;
import org.silsagusi.joonggaemoa.domain.message.service.MessageTemplateService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageTemplateCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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
	public ResponseEntity<ApiResponse<List<MessageTemplateDto.Response>>> getMessageTemplates(
		HttpServletRequest request
	) {
		List<MessageTemplateCommand> commandList = messageTemplateService.getMessageTemplateList(
			(Long)request.getAttribute("agentId")
		);

		List<MessageTemplateDto.Response> responseList = commandList.stream()
			.map(MessageTemplateDto.Response::of)
			.toList();

		return ResponseEntity.ok(ApiResponse.ok(responseList));
	}

	@PostMapping("/api/messages/templates")
	public ResponseEntity<ApiResponse<Void>> createMessageTemplate(
		HttpServletRequest request,
		@RequestBody @Valid MessageTemplateDto.Request requestDto
	) {
		messageTemplateService.createMessageTemplate(
			(Long)request.getAttribute("agentId"),
			requestDto
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/messages/templates/{templateId}")
	public ResponseEntity<ApiResponse<Void>> updateMessageTemplate(
		HttpServletRequest request,
		@PathVariable(name = "templateId") Long templateId,
		@RequestBody @Valid MessageTemplateDto.Request requestDto
	) {
		messageTemplateService.updateMessageTemplate(
			(Long)request.getAttribute("agentId"),
			templateId,
			requestDto.getTitle(),
			requestDto.getContent()
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

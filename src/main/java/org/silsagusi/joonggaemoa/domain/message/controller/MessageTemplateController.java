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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageTemplateController {

	private final MessageTemplateService messageTemplateService;

	@GetMapping("/api/message/template")
	public ResponseEntity<ApiResponse<MessageTemplateResponse>> getMessageTemplate(
		HttpServletRequest request,
		@RequestParam String category
	) {
		MessageTemplateCommand command = messageTemplateService.getMessageTemplate(
			(Long)request.getAttribute("agentId"),
			category
		);

		MessageTemplateResponse responseDto = MessageTemplateResponse.of(command);

		return ResponseEntity.ok(ApiResponse.ok(responseDto));
	}

	@PatchMapping("/api/message/template")
	public ResponseEntity<ApiResponse<MessageTemplateResponse>> updateMessageTemplate(
		HttpServletRequest request,
		@RequestBody MessageTemplateRequest requestDto
	) {
		MessageTemplateCommand command = messageTemplateService.updateMessageTemplate(
			(Long)request.getAttribute("agentId"),
			requestDto.getCategory(), requestDto.getContent()
		);

		MessageTemplateResponse responseDto = MessageTemplateResponse.of(command);

		return ResponseEntity.ok(ApiResponse.ok(responseDto));
	}

	@DeleteMapping("/api/message/template")
	public ResponseEntity<ApiResponse<Void>> deleteMessageTemplate(
		HttpServletRequest request,
		@RequestParam String category
	) {
		messageTemplateService.deleteMessageTemplate(
			(Long)request.getAttribute("agentId"),
			category
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

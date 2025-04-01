package org.silsagusi.joonggaemoa.domain.message.controller;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageTemplateDto;
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
	public ResponseEntity<ApiResponse<MessageTemplateDto.Response>> getMessageTemplate(
		HttpServletRequest request,
		@RequestParam String category
	) {
		MessageTemplateCommand command = messageTemplateService.getMessageTemplate(
			(Long)request.getAttribute("agentId"),
			category
		);

		MessageTemplateDto.Response responseDto = MessageTemplateDto.Response.of(command);

		return ResponseEntity.ok(ApiResponse.ok(responseDto));
	}

	@PatchMapping("/api/message/template")
	public ResponseEntity<ApiResponse<MessageTemplateDto.Response>> updateMessageTemplate(
		HttpServletRequest request,
		@RequestBody MessageTemplateDto.Request requestDto
	) {
		MessageTemplateCommand command = messageTemplateService.updateMessageTemplate(
			(Long)request.getAttribute("agentId"),
			requestDto.getCategory(), requestDto.getContent()
		);

		MessageTemplateDto.Response responseDto = MessageTemplateDto.Response.of(command);

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

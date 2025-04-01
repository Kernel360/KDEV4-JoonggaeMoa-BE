package org.silsagusi.joonggaemoa.domain.message.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageDto;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.ReservedMessageDto;
import org.silsagusi.joonggaemoa.domain.message.service.MessageService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/api/messages")
	public ResponseEntity<ApiResponse<List<MessageDto.Response>>> getMessage(
		HttpServletRequest request,
		@RequestParam(required = false) Long lastMessageId
	) {
		List<MessageCommand> messageCommands = messageService.getMessage(
			(Long)request.getAttribute("agentId"),
			lastMessageId
		);

		List<MessageDto.Response> responses = messageCommands.stream()
			.map(MessageDto.Response::of)
			.toList();

		return ResponseEntity.ok(ApiResponse.ok(responses));
	}

	@PostMapping("/api/messages")
	public ResponseEntity<ApiResponse<Void>> reserveMessage(
		@RequestBody ReservedMessageDto.Request request
	) {
		messageService.reserveMessage(request.getContent(), request.getSendAt(), request.getCustomerIdList());

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/reserved-message")
	public ResponseEntity<ApiResponse<List<ReservedMessageDto.Response>>> getReservedMessage(
		HttpServletRequest request,
		@RequestParam(required = false) Long lastMessageId
	) {

		List<ReservedMessageCommand> commands = messageService.getReservedMessage(
			(Long)request.getAttribute("agentId"),
			lastMessageId
		);

		List<ReservedMessageDto.Response> responses = commands.stream()
			.map(ReservedMessageDto.Response::of)
			.toList();

		return ResponseEntity.ok(ApiResponse.ok(responses));
	}
}

package org.silsagusi.joonggaemoa.domain.message.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageRequest;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageResponse;
import org.silsagusi.joonggaemoa.domain.message.service.MessageService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/api/agents/{agentId}/messages")
	public ResponseEntity<ApiResponse<List<MessageResponse>>> getMessage(
		@PathVariable Long agentId,
		@RequestParam(required = false) Long lastMessageId
	) {
		List<MessageCommand> messageCommands = messageService.getMessage(agentId, lastMessageId);

		List<MessageResponse> responses = messageCommands.stream()
			.map(command -> MessageResponse.of(command))
			.toList();

		return ResponseEntity.ok(ApiResponse.ok(responses));
	}

	@PostMapping("/api/agents/{agentId}/messages")
	public ResponseEntity<ApiResponse<Void>> reserveMessage(
		@PathVariable Long agentId,
		@RequestBody MessageRequest request
	) {
		messageService.reserveMessage(request.getContent(), request.getSendAt(), request.getCustomerIdList());

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

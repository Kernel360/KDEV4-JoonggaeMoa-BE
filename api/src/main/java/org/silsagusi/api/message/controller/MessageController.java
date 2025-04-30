package org.silsagusi.api.message.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.message.application.dto.MessageDto;
import org.silsagusi.api.message.application.dto.UpdateMessageRequest;
import org.silsagusi.api.message.application.service.MessageService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/api/all-messages")
	public ResponseEntity<ApiResponse<Page<MessageDto.Response>>> getMessagePage(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<MessageDto.Response> messagePage = messageService.getMessagePage(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(messagePage));
	}

	@PostMapping("/api/messages")
	public ResponseEntity<ApiResponse<Void>> createMessage(
		@RequestBody @Valid MessageDto.Request messageRequest
	) {
		messageService.createMessages(messageRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/messages")
	public ResponseEntity<ApiResponse<Page<MessageDto.Response>>> getReservedMessagePage(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<MessageDto.Response> responsePage = messageService.getReservedMessagePage(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(responsePage));
	}

	@PatchMapping("/api/messages/{messageId}")
	public ResponseEntity<ApiResponse<Void>> updateMessage(
		@CurrentAgentId Long agentId,
		@PathVariable Long messageId,
		@RequestBody @Valid UpdateMessageRequest updateMessageRequest
	) {
		messageService.updateMessage(agentId, messageId, updateMessageRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/messages/{messageId}")
	public ResponseEntity<ApiResponse<Void>> deleteReservedMessage(
		@CurrentAgentId Long agentId,
		@PathVariable Long messageId
	) {
		messageService.deleteReservedMessage(agentId, messageId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}

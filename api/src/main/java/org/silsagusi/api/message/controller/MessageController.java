package org.silsagusi.api.message.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.message.application.dto.CreateMessageRequest;
import org.silsagusi.api.message.application.dto.MessageResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/api/all-messages")
	public ResponseEntity<ApiResponse<Page<MessageResponse>>> getMessagePage(
		@CurrentAgentId Long agentId,
		@RequestParam(name = "search-type", required = false) String searchType,
		@RequestParam(required = false) String keyword,
		Pageable pageable
	) {
		Page<MessageResponse> messagePage = messageService.getMessagePage(agentId, searchType, keyword, pageable);
		return ResponseEntity.ok(ApiResponse.ok(messagePage));
	}

	@PostMapping("/api/messages")
	public ResponseEntity<ApiResponse<Void>> createMessage(
		@RequestBody @Valid CreateMessageRequest messageRequest
	) {
		messageService.createMessages(messageRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/messages")
	public ResponseEntity<ApiResponse<Page<MessageResponse>>> getReservedMessagePage(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<MessageResponse> responsePage = messageService.getReservedMessagePage(agentId, pageable);
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
	public ResponseEntity<ApiResponse<Void>> deleteMessage(
		@CurrentAgentId Long agentId,
		@PathVariable Long messageId
	) {
		messageService.deleteMessage(agentId, messageId);
		return ResponseEntity.ok(ApiResponse.ok());
	}
}

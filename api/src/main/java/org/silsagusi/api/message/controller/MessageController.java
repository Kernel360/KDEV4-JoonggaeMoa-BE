package org.silsagusi.api.message.controller;

import org.silsagusi.api.message.application.MessageService;
import org.silsagusi.api.message.application.dto.MessageDto;
import org.silsagusi.api.message.application.dto.MessageUpdateRequest;
import org.silsagusi.core.customResponse.ApiResponse;
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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {

	private final MessageService messageService;

	@GetMapping("/api/messages")
	public ResponseEntity<ApiResponse<Page<MessageDto.Response>>> getMessagePage(
		HttpServletRequest request,
		Pageable pageable
	) {
		Page<MessageDto.Response> messagePage = messageService.getMessagePage(
			(Long)request.getAttribute("agentId"),
			pageable
		);

		return ResponseEntity.ok(ApiResponse.ok(messagePage));
	}

	@PostMapping("/api/messages")
	public ResponseEntity<ApiResponse<Void>> createMessage(
		@RequestBody @Valid MessageDto.Request messageRequest
	) {
		messageService.createMessage(messageRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/messages/{messageId}")
	public ResponseEntity<ApiResponse<Void>> updateMessage(
		HttpServletRequest request,
		@PathVariable Long messageId,
		@RequestBody @Valid MessageUpdateRequest messageUpdateRequest
	) {
		messageService.updateMessage(
			(Long)request.getAttribute("agentId"),
			messageId,
			messageUpdateRequest
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/reserved-messages")
	public ResponseEntity<ApiResponse<Page<MessageDto.Response>>> getReservedMessagePage(
		HttpServletRequest request,
		Pageable pageable
	) {
		Page<MessageDto.Response> responsePage = messageService.getReservedMessagePage(
			(Long)request.getAttribute("agentId"),
			pageable
		);

		return ResponseEntity.ok(ApiResponse.ok(responsePage));
	}

	@GetMapping("/api/reserved-messages/{messageId}")
	public ResponseEntity<ApiResponse<MessageDto.Response>> getReservedMessage(
		@PathVariable Long messageId
	) {
		MessageDto.Response response = messageService.getReservedMessage(messageId);

		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@DeleteMapping("/api/reserved-messages/{messageId}")
	public ResponseEntity<ApiResponse<Void>> deleteReservedMessage(
		HttpServletRequest request,
		@PathVariable Long messageId
	) {
		messageService.deleteReservedMessage(
			(Long)request.getAttribute("agentId"),
			messageId
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

package org.silsagusi.joonggaemoa.domain.message.controller;

import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageDto;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.ReservedMessageDto;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.ReservedMessageUpdateRequest;
import org.silsagusi.joonggaemoa.domain.message.service.MessageService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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
		Pageable pageable
	) {
		Page<MessageCommand> messageCommandPage = messageService.getMessagePage(pageable);

		Page<MessageDto.Response> responses = messageCommandPage.map(MessageDto.Response::of);

		return ResponseEntity.ok(ApiResponse.ok(responses));
	}

	@PostMapping("/api/reserved-messages")
	public ResponseEntity<ApiResponse<Void>> createReserveMessage(
		@RequestBody @Valid ReservedMessageDto.Request requestDto
	) {
		messageService.createReservedMessage(requestDto.getContent(), requestDto.getSendAt(),
			requestDto.getCustomerIdList());
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/reserved-messages/{reservedMessageId}")
	public ResponseEntity<ApiResponse<Void>> updateReserveMessage(
		HttpServletRequest request,
		@PathVariable Long reservedMessageId,
		@RequestBody @Valid ReservedMessageUpdateRequest requestDto
	) {
		messageService.updateReservedMessage(
			(Long)request.getAttribute("agentId"),
			reservedMessageId,
			requestDto.getSendAt(),
			requestDto.getContent()
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/reserved-messages")
	public ResponseEntity<ApiResponse<Page<ReservedMessageDto.Response>>> getReservedMessagePage(
		Pageable pageable
	) {
		Page<ReservedMessageCommand> commandPage = messageService.getReservedMessagePage(pageable);

		Page<ReservedMessageDto.Response> responsePage = commandPage
			.map(ReservedMessageDto.Response::of);

		return ResponseEntity.ok(ApiResponse.ok(responsePage));
	}

	@GetMapping("/api/reserved-messages/{reservedMessageId}")
	public ResponseEntity<ApiResponse<ReservedMessageDto.Response>> getReservedMessage(
		@PathVariable Long reservedMessageId
	) {
		ReservedMessageCommand command = messageService.getReservedMessage(reservedMessageId);

		return ResponseEntity.ok(ApiResponse.ok(ReservedMessageDto.Response.of(command)));
	}

	@DeleteMapping("/api/reserved-messages/{reservedMessageId}")
	public ResponseEntity<ApiResponse<Void>> deleteReservedMessage(
		HttpServletRequest request,
		@PathVariable Long reservedMessageId
	) {
		messageService.deleteReservedMessage(
			(Long)request.getAttribute("agentId"),
			reservedMessageId
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}
}

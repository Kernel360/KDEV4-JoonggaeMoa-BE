package org.silsagusi.api.inquiry.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.inquiry.application.dto.ChatDto;
import org.silsagusi.api.inquiry.application.dto.InquiryAnswerDto;
import org.silsagusi.api.inquiry.application.dto.InquiryDto;
import org.silsagusi.api.inquiry.application.service.InquiryService;
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
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class InquiryController {

	private final InquiryService inquiryService;

	@PostMapping("/api/inquiries")
	public ResponseEntity<ApiResponse<Void>> createInquiry(
		@RequestBody @Valid InquiryDto.CreateRequest inquiryCreateRequest
	) {
		inquiryService.createInquiry(inquiryCreateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> updateInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryDto.UpdateRequest updateInquiryRequest
	) {
		inquiryService.updateInquiry(inquiryId, updateInquiryRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> deleteInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryDto.PasswordRequest passwordRequest
	) {
		inquiryService.deleteInquiry(inquiryId, passwordRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/inquiries")
	public ResponseEntity<ApiResponse<Page<InquiryDto.Response>>> getInquiries(
		Pageable pageable
	) {
		Page<InquiryDto.Response> inquiries = inquiryService.getAllInquiry(pageable);
		return ResponseEntity.ok(ApiResponse.ok(inquiries));
	}

	@GetMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<InquiryDto.Response>> getInquiry(
		@PathVariable("inquiryId") Long inquiryId
	) {
		InquiryDto.Response inquiry = inquiryService.getInquiry(inquiryId);
		return ResponseEntity.ok(ApiResponse.ok(inquiry));
	}

	@PostMapping("/api/inquiries-answers/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> writeInquiryAnswer(
		@CurrentAgentId Long agentId,
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryAnswerDto.Request inquiryAnswerRequest
	) {
		inquiryService.createInquiryAnswer(agentId, inquiryId, inquiryAnswerRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/inquiries/consultations")
	public ResponseEntity<ApiResponse<Void>> createConsultation(
		@RequestBody @Valid InquiryDto.ConsultationRequest inquiryConsultationRequest
	) {
		inquiryService.createConsultation(inquiryConsultationRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/inquiries/chat")
	public Mono<ApiResponse<ChatDto.Response>> chat(
		@RequestBody ChatDto.Request chatRequest
	) {
		return inquiryService.chat(chatRequest).map(ApiResponse::ok);
	}

}

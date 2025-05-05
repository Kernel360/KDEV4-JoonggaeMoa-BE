package org.silsagusi.api.inquiry.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.inquiry.application.dto.Chat;
import org.silsagusi.api.inquiry.application.dto.CreateConsultationRequest;
import org.silsagusi.api.inquiry.application.dto.CreateInquiryAnswerRequest;
import org.silsagusi.api.inquiry.application.dto.CreateInquiryRequest;
import org.silsagusi.api.inquiry.application.dto.DeleteInquiryRequest;
import org.silsagusi.api.inquiry.application.dto.InquiryDetailResponse;
import org.silsagusi.api.inquiry.application.dto.InquiryResponse;
import org.silsagusi.api.inquiry.application.dto.UpdateInquiryRequest;
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

@RequiredArgsConstructor
@RestController
public class InquiryController {

	private final InquiryService inquiryService;

	@PostMapping("/api/inquiries")
	public ResponseEntity<ApiResponse<Void>> createInquiry(
		@RequestBody @Valid CreateInquiryRequest inquiryCreateRequest
	) {
		inquiryService.createInquiry(inquiryCreateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> updateInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid UpdateInquiryRequest updateInquiryRequest
	) {
		inquiryService.updateInquiry(inquiryId, updateInquiryRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> deleteInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid DeleteInquiryRequest deleteInquiryRequest
	) {
		inquiryService.deleteInquiry(inquiryId, deleteInquiryRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/inquiries")
	public ResponseEntity<ApiResponse<Page<InquiryResponse>>> getInquiries(
		Pageable pageable
	) {
		Page<InquiryResponse> inquiries = inquiryService.getAllInquiry(pageable);
		return ResponseEntity.ok(ApiResponse.ok(inquiries));
	}

	@GetMapping("/api/inquiries/{inquiryId}")
	public ResponseEntity<ApiResponse<InquiryDetailResponse>> getInquiry(
		@PathVariable("inquiryId") Long inquiryId
	) {
		InquiryDetailResponse inquiryResponse = inquiryService.getInquiry(inquiryId);
		return ResponseEntity.ok(ApiResponse.ok(inquiryResponse));
	}

	@PostMapping("/api/inquiries-answers/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> writeInquiryAnswer(
		@CurrentAgentId Long agentId,
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid CreateInquiryAnswerRequest inquiryAnswerRequest
	) {
		inquiryService.createInquiryAnswer(agentId, inquiryId, inquiryAnswerRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/inquiries/consultations")
	public ResponseEntity<ApiResponse<Void>> createConsultation(
		@RequestBody @Valid CreateConsultationRequest inquiryConsultationRequest
	) {
		inquiryService.createConsultation(inquiryConsultationRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PostMapping("/api/inquiries/chat")
	public ResponseEntity<ApiResponse<Chat.Response>> chat(
		@RequestBody Chat.Request chatRequest
	) {
		Chat.Response chatResponse = inquiryService.chat(chatRequest);
		return ResponseEntity.ok(ApiResponse.ok(chatResponse));
	}

}

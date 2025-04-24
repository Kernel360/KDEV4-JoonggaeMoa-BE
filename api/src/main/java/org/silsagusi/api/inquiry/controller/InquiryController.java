package org.silsagusi.api.inquiry.controller;

import java.util.List;

import org.silsagusi.api.inquiry.application.dto.InquiryAnswerDto;
import org.silsagusi.api.inquiry.application.dto.InquiryDto;
import org.silsagusi.api.inquiry.application.service.InquiryService;
import org.silsagusi.api.response.ApiResponse;
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

@RequiredArgsConstructor
@RestController
public class InquiryController {

	private final InquiryService inquiryService;

	@PostMapping("/api/inquiry")
	public ResponseEntity<ApiResponse<Void>> createInquiry(
		@RequestBody @Valid InquiryDto.CreateRequest inquiryCreateRequest
	) {
		inquiryService.createInquiry(inquiryCreateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/inquiry/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> updateInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryDto.UpdateRequest updateInquiryRequest
	) {
		inquiryService.updateInquiry(inquiryId, updateInquiryRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/inquiry/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> deleteInquiry(
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryDto.PasswordRequest passwordRequest
	) {
		inquiryService.deleteInquiry(inquiryId, passwordRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/inquiry")
	public ResponseEntity<ApiResponse<List<InquiryDto.Response>>> getInquiries(
		Pageable pageable
	) {
		List<InquiryDto.Response> inquiries = inquiryService.getAllInquiry(pageable);
		return ResponseEntity.ok(ApiResponse.ok(inquiries));
	}

	@GetMapping("/api/inquiry/{inquiryId}")
	public ResponseEntity<ApiResponse<InquiryDto.Response>> updateConsultation(
		@PathVariable("inquiryId") Long inquiryId
	) {
		InquiryDto.Response inquiry = inquiryService.getInquiry(inquiryId);
		return ResponseEntity.ok(ApiResponse.ok(inquiry));
	}

	@PostMapping("/api/inquiry/{inquiryId}")
	public ResponseEntity<ApiResponse<Void>> writeInquiryAnswer(
		HttpServletRequest request,
		@PathVariable("inquiryId") Long inquiryId,
		@RequestBody @Valid InquiryAnswerDto.Request inquiryAnswerRequest
	) {
		inquiryService.createInquiryAnswer((Long)request.getAttribute("agentId"), inquiryId, inquiryAnswerRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

}

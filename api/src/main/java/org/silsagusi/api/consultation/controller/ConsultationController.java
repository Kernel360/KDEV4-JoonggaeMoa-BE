package org.silsagusi.api.consultation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.consultation.application.dto.ConsultationHistoryResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.CreateConsultationRequest;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.api.consultation.application.service.ConsultationService;
import org.silsagusi.api.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
public class ConsultationController {

	private final ConsultationService consultationService;

	@PostMapping("/api/consultations")
	public ResponseEntity<ApiResponse<Void>> createConsultation(
		@RequestBody @Valid CreateConsultationRequest createConsultationRequest
	) {
		consultationService.createConsultation(createConsultationRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<ConsultationResponse>> getConsultation(
		@PathVariable Long consultationId
	) {
		ConsultationResponse consultationResponse = consultationService.getConsultation(consultationId);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponse));
	}

	@GetMapping("/api/consultations/status")
	public ResponseEntity<ApiResponse<List<ConsultationResponse>>> getConsultationsByStatus(
		@CurrentAgentId Long agentId,
		@RequestParam String month, // 형식 : yyyy-MM
		@RequestParam String status
	) {
		List<ConsultationResponse> consultationResponses = consultationService.getConsultationsByStatus(
			agentId, month, status);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponses));
	}

	@GetMapping("/api/consultations/date")
	public ResponseEntity<ApiResponse<List<ConsultationResponse>>> getAllConsultationsByDate(
		@CurrentAgentId Long agentId,
		@RequestParam LocalDateTime date
	) {
		List<ConsultationResponse> consultationResponseList = consultationService.getAllConsultationsByDate(
			agentId, date);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
	}

	@GetMapping("/api/consultations/{consultationId}/customers")
	public ResponseEntity<ApiResponse<ConsultationHistoryResponse>> getConsultationsByCustomer(
		@PathVariable("consultationId") Long consultationId,
		Pageable pageable
	) {
		ConsultationHistoryResponse consultationHistoryResponse = consultationService.getConsultationsByCustomer(
			consultationId,
			pageable);
		return ResponseEntity.ok(ApiResponse.ok(consultationHistoryResponse));
	}

	@GetMapping("/api/consultations/month-inform")
	public ResponseEntity<ApiResponse<ConsultationMonthResponse>> getMonthInform(
		@CurrentAgentId Long agentId,
		@RequestParam String month //date형식: yyyy-MM
	) {
		ConsultationMonthResponse consultationMonthInformResponse = consultationService.getMonthInformation(
			agentId, month);
		return ResponseEntity.ok(ApiResponse.ok(consultationMonthInformResponse));
	}

	@PatchMapping("/api/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<Void>> updateConsultation(
		@CurrentAgentId Long agentId,
		@PathVariable("consultationId") Long consultationId,
		@RequestBody @Valid UpdateConsultationRequest updateConsultationRequest
	) {
		consultationService.updateConsultation(agentId, consultationId, updateConsultationRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/consultations/{consultationId}/status")
	public ResponseEntity<ApiResponse<Void>> updateConsultationStatus(
		@CurrentAgentId Long agentId,
		@PathVariable("consultationId") Long consultationId,
		@RequestParam String consultationStatus
	) {
		consultationService.updateConsultationStatus(agentId, consultationId, consultationStatus);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/dashboard/consultation-summary")
	public ResponseEntity<ApiResponse<ConsultationSummaryResponse>> getConsultationSummary(
		@CurrentAgentId Long agentId
	) {
		ConsultationSummaryResponse consultationSummaryResponse = consultationService.getConsultationSummary(agentId);
		return ResponseEntity.ok(ApiResponse.ok(consultationSummaryResponse));
	}

}

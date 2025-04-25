package org.silsagusi.api.consultation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.consultation.application.ConsultationService;
import org.silsagusi.api.consultation.application.dto.ConsultationDto;
import org.silsagusi.api.consultation.application.dto.ConsultationMonthResponse;
import org.silsagusi.api.consultation.application.dto.ConsultationSummaryResponse;
import org.silsagusi.api.consultation.application.dto.UpdateConsultationRequest;
import org.silsagusi.api.customResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ConsultationController {

	private final ConsultationService consultationService;

	@PostMapping("/api/consultations")
	public ResponseEntity<ApiResponse<Void>> createConsultation(
		@RequestBody @Valid ConsultationDto.Request consultationRequestDto
	) {
		consultationService.createConsultation(consultationRequestDto);
		return ResponseEntity.ok(ApiResponse.ok());

	}

	@GetMapping("/api/consultations/date")
	public ResponseEntity<ApiResponse<List<ConsultationDto.Response>>> getAllConsultationsByDate(
		HttpServletRequest request,
		@RequestParam LocalDateTime date
	) {
		List<ConsultationDto.Response> consultationResponseList = consultationService.getAllConsultationsByDate(
			(Long)request.getAttribute("agentId"),
			date
		);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
	}

	@GetMapping("/api/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<ConsultationDto.Response>> getConsultation(
		@PathVariable("consultationId") Long consultationId
	) {
		ConsultationDto.Response consultationResponse = consultationService.getConsultation(consultationId);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponse));
	}

	@GetMapping("/api/consultations/month-inform")
	public ResponseEntity<ApiResponse<ConsultationMonthResponse>> getMonthInform(
		HttpServletRequest request,
		@RequestParam String month //date형식: yyyy-MM
	) {
		ConsultationMonthResponse consultationMonthInformResponse = consultationService.getMonthInformation(
			(Long)request.getAttribute("agentId"),
			month
		);
		return ResponseEntity.ok(ApiResponse.ok(consultationMonthInformResponse));
	}

	@PatchMapping("/api/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<Void>> updateConsultation(
		HttpServletRequest request,
		@PathVariable("consultationId") Long consultationId,
		@RequestBody @Valid UpdateConsultationRequest updateConsultationRequest
	) {
		consultationService.updateConsultation(
			(Long)request.getAttribute("agentId"),
			consultationId,
			updateConsultationRequest
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/consultations/{consultationId}/status")
	public ResponseEntity<ApiResponse<Void>> updateConsultationStatus(
		HttpServletRequest request,
		@PathVariable("consultationId") Long consultationId,
		@RequestParam String consultationStatus
	) {
		consultationService.updateConsultationStatus(
			(Long)request.getAttribute("agentId"),
			consultationId, consultationStatus);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/dashboard/consultation-summary")
	public ResponseEntity<ApiResponse<ConsultationSummaryResponse>> getConsultationSummary(
		HttpServletRequest request
	) {
		return ResponseEntity.ok(ApiResponse.ok(
			consultationService.getConsultationSummary(
				(Long)request.getAttribute("agentId")
			)
		));
	}

}

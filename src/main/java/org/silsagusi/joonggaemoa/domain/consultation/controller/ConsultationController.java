package org.silsagusi.joonggaemoa.domain.consultation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.ConsultationMonthInformResponse;
import org.silsagusi.joonggaemoa.domain.consultation.controller.dto.ConsultationDto;
import org.silsagusi.joonggaemoa.domain.consultation.controller.dto.UpdateConsultationRequest;
import org.silsagusi.joonggaemoa.domain.consultation.service.ConsultationService;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationMonthInformCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
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
		@RequestBody @Valid ConsultationDto.Request requestDto
	) {
		consultationService.createConsultation(
			requestDto.getCustomerId(),
			requestDto.getDate()
		);
		return ResponseEntity.ok(ApiResponse.ok());

	}

	@GetMapping("/api/consultations/date")
	public ResponseEntity<ApiResponse<List<ConsultationDto.Response>>> getAllConsultationsByDate(
		HttpServletRequest request,
		@RequestParam LocalDateTime date
	) {
		List<ConsultationCommand> consultationCommandList = consultationService.getAllConsultationsByDate(
			(Long)request.getAttribute("agentId"),
			date
		);
		List<ConsultationDto.Response> consultationResponseList = consultationCommandList.stream()
			.map(ConsultationDto.Response::of).toList();
		return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
	}

	@GetMapping("/api/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<ConsultationDto.Response>> getConsultation(
		@PathVariable("consultationId") Long consultationId
	) {
		ConsultationCommand consultationCommand = consultationService.getConsultation(consultationId);
		ConsultationDto.Response consultationResponse = ConsultationDto.Response.of(consultationCommand);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponse));
	}

	@GetMapping("/api/consultations/month-inform")
	public ResponseEntity<ApiResponse<ConsultationMonthInformResponse>> getMonthInform(
		HttpServletRequest request,
		@RequestParam String month //date형식: yyyy-MM
	) {
		ConsultationMonthInformCommand consultationMonthInformCommand = consultationService.getMonthInformation(
			(Long)request.getAttribute("agentId"),
			month
		);
		ConsultationMonthInformResponse consultationMonthInformResponse = ConsultationMonthInformResponse.of(
			consultationMonthInformCommand);
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
			updateConsultationRequest.getDate(),
			updateConsultationRequest.getPurpose(),
			updateConsultationRequest.getInterestProperty(),
			updateConsultationRequest.getInterestLocation(),
			updateConsultationRequest.getContractType(),
			updateConsultationRequest.getAssetStatus(),
			updateConsultationRequest.getMemo(),
			updateConsultationRequest.getConsultationStatus()
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

}

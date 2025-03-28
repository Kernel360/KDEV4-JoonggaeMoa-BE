package org.silsagusi.joonggaemoa.domain.consultation.contorller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.ConsultationResponse;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.ConsultationStatusResponse;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.CreateConsultationRequest;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.UpdateConsultationRequest;
import org.silsagusi.joonggaemoa.domain.consultation.service.ConsultationService;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationStatusCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ConsultationApiController {

	private final ConsultationService consultationService;

	@PostMapping("/api/agents/{agentId}/consultations")
	public ResponseEntity<ApiResponse<Void>> createConsultation(
		@RequestBody CreateConsultationRequest createConsultationRequest
	) {
		consultationService.createConsultation(
			createConsultationRequest.getCustomerId(),
			createConsultationRequest.getDate()
		);
		return ResponseEntity.ok(ApiResponse.ok());

	}

	@GetMapping("/api/agents/{agentId}/consultations")
	public ResponseEntity<ApiResponse<List<ConsultationResponse>>> getAllConsultation() {
		List<ConsultationCommand> consultationCommandList = consultationService.getAllConsultations();
		List<ConsultationResponse> consultationResponseList = consultationCommandList.stream()
			.map(it -> ConsultationResponse.of(it)).toList();
		return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
	}

	@GetMapping("/api/agents/{agentId}/consultations/status")
	public ResponseEntity<ApiResponse<List<ConsultationResponse>>> getAllConsultationByStatus(
		@RequestParam String consultationStatus
	) {
		List<ConsultationCommand> consultationCommandList = consultationService.getConsultationsByStatus(
			consultationStatus);
		List<ConsultationResponse> consultationResponseList = consultationCommandList.stream()
			.map(it -> ConsultationResponse.of(it)).toList();
		return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
	}

	@GetMapping("/api/agents/{agentId}/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<ConsultationResponse>> getConsultation(
		@PathVariable("consultationId") Long consultationId
	) {
		ConsultationCommand consultationCommand = consultationService.getConsultation(consultationId);
		ConsultationResponse consultationResponse = ConsultationResponse.of(consultationCommand);
		return ResponseEntity.ok(ApiResponse.ok(consultationResponse));
	}

	@GetMapping("/api/agents/{agentId}/consultations/status-inform")
	public ResponseEntity<ApiResponse<ConsultationStatusResponse>> getStatusInformation() {
		ConsultationStatusCommand consultationStatusCommand = consultationService.getStatusInformation();
		ConsultationStatusResponse consultationStatusResponse = ConsultationStatusResponse.of(
			consultationStatusCommand);
		return ResponseEntity.ok(ApiResponse.ok(consultationStatusResponse));
	}

	@PatchMapping("/api/agents/{agentId}/consultations/{consultationId}")
	public ResponseEntity<ApiResponse<Void>> updateConsultation(
		@PathVariable("consultationId") Long consultationId,
		@RequestBody UpdateConsultationRequest updateConsultationRequest
	) {
		consultationService.updateConsultation(
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

	@PatchMapping("/api/agents/{agentId}/consultations/{consultationId}/status")
	public ResponseEntity<ApiResponse<Void>> updateConsultationStatus(
		@PathVariable("consultationId") Long consultationId,
		@RequestParam String consultationStatus
	) {
		consultationService.updateConsultationStatus(consultationId, consultationStatus);
		return ResponseEntity.ok(ApiResponse.ok());
	}

}

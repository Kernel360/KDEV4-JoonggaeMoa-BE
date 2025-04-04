package org.silsagusi.joonggaemoa.domain.consultation.contorller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.ConsultationDto;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.ConsultationStatusResponse;
import org.silsagusi.joonggaemoa.domain.consultation.contorller.dto.UpdateConsultationRequest;
import org.silsagusi.joonggaemoa.domain.consultation.service.ConsultationService;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationCommand;
import org.silsagusi.joonggaemoa.domain.consultation.service.command.ConsultationStatusCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/api/consultations")
    public ResponseEntity<ApiResponse<List<ConsultationDto.Response>>> getAllConsultation(
        @RequestParam LocalDateTime date
    ) {
        List<ConsultationCommand> consultationCommandList = consultationService.getAllConsultationsByDate(date);
        List<ConsultationDto.Response> consultationResponseList = consultationCommandList.stream()
            .map(ConsultationDto.Response::of).toList();
        return ResponseEntity.ok(ApiResponse.ok(consultationResponseList));
    }

    @GetMapping("/api/consultations/status")
    public ResponseEntity<ApiResponse<List<ConsultationDto.Response>>> getAllConsultationByStatus(
        @RequestParam String consultationStatus
    ) {
        List<ConsultationCommand> consultationCommandList = consultationService.getConsultationsByStatus(
            consultationStatus);
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


    @GetMapping("/api/consultations/status-inform")
    public ResponseEntity<ApiResponse<ConsultationStatusResponse>> getStatusInformation() {
        ConsultationStatusCommand consultationStatusCommand = consultationService.getStatusInformation();
        ConsultationStatusResponse consultationStatusResponse = ConsultationStatusResponse.of(
            consultationStatusCommand);
        return ResponseEntity.ok(ApiResponse.ok(consultationStatusResponse));
    }

    @GetMapping("/api/consultations/date-count")
    public ResponseEntity<ApiResponse<List<Integer>>> getDateCount(
        @RequestParam String date //date형식: yyyy-MM
    ) {
        List<Integer> countList = consultationService.getDateCount(date);
        return ResponseEntity.ok(ApiResponse.ok(countList));
    }

    @PatchMapping("/api/consultations/{consultationId}")
    public ResponseEntity<ApiResponse<Void>> updateConsultation(
        @PathVariable("consultationId") Long consultationId,
        @RequestBody @Valid UpdateConsultationRequest updateConsultationRequest
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

    @PatchMapping("/api/consultations/{consultationId}/status")
    public ResponseEntity<ApiResponse<Void>> updateConsultationStatus(
        @PathVariable("consultationId") Long consultationId,
        @RequestParam String consultationStatus
    ) {
        consultationService.updateConsultationStatus(consultationId, consultationStatus);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}

package org.silsagusi.api.survey.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.response.ApiResponse;
import org.silsagusi.api.survey.application.dto.AnswerResponse;
import org.silsagusi.api.survey.application.dto.CreateSurveyRequest;
import org.silsagusi.api.survey.application.dto.SubmitAnswerRequest;
import org.silsagusi.api.survey.application.dto.SurveyResponse;
import org.silsagusi.api.survey.application.dto.UpdateSurveyRequest;
import org.silsagusi.api.survey.application.service.SurveyService;
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
public class SurveyController {

	private final SurveyService surveyService;

	@PostMapping("/api/surveys")
	public ResponseEntity<ApiResponse<Void>> createSurvey(
		@CurrentAgentId Long agentId,
		@RequestBody @Valid CreateSurveyRequest surveyCreateRequest
	) {
		surveyService.createSurvey(agentId, surveyCreateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> deleteSurvey(
		@CurrentAgentId Long agentId,
		@PathVariable("surveyId") String surveyId
	) {
		surveyService.deleteSurvey(agentId, surveyId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> updateSurvey(
		@CurrentAgentId Long agentId,
		@PathVariable String surveyId,
		@RequestBody @Valid UpdateSurveyRequest surveyUpdateRequest
	) {
		surveyService.updateSurvey(agentId, surveyId, surveyUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/surveys")
	public ResponseEntity<ApiResponse<Page<SurveyResponse>>> getAllSurveys(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<SurveyResponse> surveyResponsePage = surveyService.getAllSurveys(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(surveyResponsePage));
	}

	@GetMapping("/api/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<SurveyResponse>> getSurvey(
		@PathVariable("surveyId") String surveyId
	) {
		SurveyResponse response = surveyService.findById(surveyId);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/api/surveys/answers")
	public ResponseEntity<ApiResponse<Page<AnswerResponse>>> getSurveyAnswers(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<AnswerResponse> answerResponsePage = surveyService.getAllAnswers(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(answerResponsePage));
	}

	// 고객용 api
	@GetMapping("/api/customers/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<SurveyResponse>> getSurveyForCustomer(
		@PathVariable("surveyId") String surveyId
	) {
		SurveyResponse surveyCommand = surveyService.findById(surveyId);
		return ResponseEntity.ok(ApiResponse.ok(surveyCommand));
	}

	@PostMapping("/api/customers/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> submitSurveyAnswer(
		@PathVariable("surveyId") String surveyId,
		@RequestBody @Valid SubmitAnswerRequest submitAnswerRequest
	) {
		surveyService.submitSurveyAnswer(surveyId, submitAnswerRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

}

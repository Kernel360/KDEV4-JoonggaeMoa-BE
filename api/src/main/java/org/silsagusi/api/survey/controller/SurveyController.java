package org.silsagusi.api.survey.controller;

import org.silsagusi.api.common.annotation.CurrentAgentId;
import org.silsagusi.api.response.ApiResponse;
import org.silsagusi.api.survey.application.dto.AnswerDto;
import org.silsagusi.api.survey.application.dto.SurveyDto;
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
		@RequestBody @Valid SurveyDto.CreateRequest surveyCreateRequest
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
		@RequestBody @Valid SurveyDto.UpdateRequest surveyUpdateRequest
	) {
		surveyService.updateSurvey(agentId, surveyId, surveyUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/surveys")
	public ResponseEntity<ApiResponse<Page<SurveyDto.Response>>> getAllSurveys(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<SurveyDto.Response> surveyResponsePage = surveyService.getAllSurveys(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(surveyResponsePage));
	}

	@GetMapping("/api/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<SurveyDto.Response>> getSurvey(
		@PathVariable("surveyId") String surveyId
	) {
		SurveyDto.Response response = surveyService.findById(surveyId);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/api/surveys/answers")
	public ResponseEntity<ApiResponse<Page<AnswerDto.Response>>> getSurveyAnswers(
		@CurrentAgentId Long agentId,
		Pageable pageable
	) {
		Page<AnswerDto.Response> answerResponsePage = surveyService.getAllAnswers(agentId, pageable);
		return ResponseEntity.ok(ApiResponse.ok(answerResponsePage));
	}

	// 고객용 api
	@GetMapping("/api/customers/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<SurveyDto.Response>> getSurveyForCustomer(
		@PathVariable("surveyId") String surveyId
	) {
		SurveyDto.Response surveyCommand = surveyService.findById(surveyId);
		return ResponseEntity.ok(ApiResponse.ok(surveyCommand));
	}

	@PostMapping("/api/customers/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> submitSurveyAnswer(
		@PathVariable("surveyId") String surveyId,
		@RequestBody @Valid AnswerDto.Request answerRequest
	) {
		surveyService.submitSurveyAnswer(surveyId, answerRequest);
		return ResponseEntity.ok(ApiResponse.ok());
	}

}

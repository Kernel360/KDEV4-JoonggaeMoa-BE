package org.silsagusi.joonggaemoa.domain.survey.controller;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.survey.controller.dto.SurveyCreateRequest;
import org.silsagusi.joonggaemoa.domain.survey.controller.dto.SurveyResponse;
import org.silsagusi.joonggaemoa.domain.survey.controller.dto.SurveyUpdateRequest;
import org.silsagusi.joonggaemoa.domain.survey.service.SurveyService;
import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;
import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SurveyController {

	private final SurveyService surveyService;

	@PostMapping("/api/agents/{agentId}/surveys")
	public ResponseEntity<ApiResponse<Void>> createSurvey(
		@PathVariable("agentId") Long agentId,
		@RequestBody SurveyCreateRequest surveyCreateRequest
	) {
		List<QuestionCommand> questionCommandList = surveyCreateRequest.getQuestionList()
			.stream().map(it -> QuestionCommand.builder()
				.content(it.getContent())
				.type(it.getType())
				.isRequired(it.getIsRequired())
				.options(it.getOptions())
				.build()
			).toList();

		surveyService.createSurvey(
			agentId,
			surveyCreateRequest.getTitle(),
			surveyCreateRequest.getDescription(),
			questionCommandList
		);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@DeleteMapping("/api/agents/{agentId}/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> deleteSurvey(
		@PathVariable("surveyId") Long surveyId
	) {
		surveyService.deleteSurvey(surveyId);
		return ResponseEntity.ok(ApiResponse.ok());
	}

	@PatchMapping("/api/agents/{agentId}/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<Void>> updateSurvey(
		@PathVariable("surveyId") Long surveyId,
		@RequestBody SurveyUpdateRequest surveyUpdateRequest
	) {
		List<QuestionCommand> questionCommandList = surveyUpdateRequest.getQuestionList()
			.stream().map(it -> QuestionCommand.builder()
				.id(it.getId())
				.content(it.getContent())
				.type(it.getType())
				.isRequired(it.getIsRequired())
				.options(it.getOptions())
				.build()
			).toList();

		surveyService.updateSurvey(
			surveyId,
			surveyUpdateRequest.getTitle(),
			surveyUpdateRequest.getDescription(),
			questionCommandList
		);

		return ResponseEntity.ok(ApiResponse.ok());
	}

	@GetMapping("/api/agents/{agentId}/surveys")
	public ResponseEntity<ApiResponse<List<SurveyResponse>>> getAllSurveys() {
		List<SurveyCommand> surveyCommandList = surveyService.getAllSurveys();
		List<SurveyResponse> surveyResponseList = surveyCommandList.stream()
			.map(it -> SurveyResponse.of(it)).toList();
		return ResponseEntity.ok(ApiResponse.ok(surveyResponseList));
	}

	@GetMapping("/api/agents/{agentId}/surveys/{surveyId}")
	public ResponseEntity<ApiResponse<SurveyResponse>> getSurvey(
		@PathVariable("surveyId") Long surveyId
	) {
		SurveyCommand surveyCommand = surveyService.findById(surveyId);
		return ResponseEntity.ok(ApiResponse.ok(SurveyResponse.of(surveyCommand)));
	}

}

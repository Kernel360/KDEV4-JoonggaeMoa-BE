package org.silsagusi.joonggaemoa.api.survey.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.api.survey.application.SurveyService;
import org.silsagusi.joonggaemoa.api.survey.application.dto.AnswerDto;
import org.silsagusi.joonggaemoa.api.survey.application.dto.SurveyDto;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SurveyController {

    private final SurveyService surveyService;

    @PostMapping("/api/surveys")
    public ResponseEntity<ApiResponse<Void>> createSurvey(
        HttpServletRequest request,
        @RequestBody @Valid SurveyDto.CreateRequest surveyCreateRequest
    ) {
        surveyService.createSurvey(
            (Long) request.getAttribute("agentId"),
            surveyCreateRequest
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<Void>> deleteSurvey(
        HttpServletRequest request,
        @PathVariable("surveyId") String surveyId
    ) {
        surveyService.deleteSurvey(
            (Long) request.getAttribute("agentId"),
            surveyId
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PatchMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<Void>> updateSurvey(
        HttpServletRequest request,
        @PathVariable String surveyId,
        @RequestBody @Valid SurveyDto.UpdateRequest surveyUpdateRequest
    ) {
        surveyService.updateSurvey(
            (Long) request.getAttribute("agentId"),
            surveyId,
            surveyUpdateRequest
        );

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/surveys")
    public ResponseEntity<ApiResponse<Page<SurveyDto.Response>>> getAllSurveys(
        HttpServletRequest request,
        Pageable pageable
    ) {
        Page<SurveyDto.Response> surveyResponsePage = surveyService.getAllSurveys(
            (Long) request.getAttribute("agentId"),
            pageable
        );
        return ResponseEntity.ok(ApiResponse.ok(surveyResponsePage));
    }

    @GetMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<SurveyDto.Response>> getSurvey(
        @PathVariable("surveyId") String surveyId
    ) {
        SurveyDto.Response response = surveyService.findById(surveyId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/api/surveys/answer")
    public ResponseEntity<ApiResponse<Page<AnswerDto.Response>>> getSurveyAnswers(
        HttpServletRequest request,
        Pageable pageable
    ) {
        Page<AnswerDto.Response> answerResponsePage = surveyService.getAllAnswers(
            (Long) request.getAttribute("agentId"),
            pageable
        );
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

    @PostMapping("/api/customers/surveys/{surveyId}/submit")
    public ResponseEntity<ApiResponse<Void>> submitSurveyAnswer(
        @PathVariable("surveyId") String surveyId,
        @RequestBody @Valid AnswerDto.Request answerRequest
    ) {
        surveyService.submitSurveyAnswer(surveyId, answerRequest);
        return ResponseEntity.ok(ApiResponse.ok());
    }

}

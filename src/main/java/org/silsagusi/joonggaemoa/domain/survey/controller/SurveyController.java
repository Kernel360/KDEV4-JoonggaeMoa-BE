package org.silsagusi.joonggaemoa.domain.survey.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.survey.controller.dto.AnswerDto;
import org.silsagusi.joonggaemoa.domain.survey.controller.dto.SurveyDto;
import org.silsagusi.joonggaemoa.domain.survey.service.SurveyService;
import org.silsagusi.joonggaemoa.domain.survey.service.command.AnswerCommand;
import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;
import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SurveyController {

    private final SurveyService surveyService;


    @PostMapping("/api/surveys")
    public ResponseEntity<ApiResponse<Void>> createSurvey(
            HttpServletRequest request,
            @RequestBody @Valid SurveyDto.CreateRequest createRequestDto
    ) {
        List<QuestionCommand> questionCommandList = createRequestDto.getQuestionList()
                .stream().map(it -> QuestionCommand.builder()
                        .content(it.getContent())
                        .type(it.getType())
                        .isRequired(it.getIsRequired())
                        .options(it.getOptions())
                        .build()
                ).toList();

        surveyService.createSurvey(
                (Long) request.getAttribute("agentId"),
                createRequestDto.getTitle(),
                createRequestDto.getDescription(),
                questionCommandList
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @DeleteMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<Void>> deleteSurvey(
            @PathVariable("surveyId") Long surveyId
    ) {
        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @PatchMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<Void>> updateSurvey(
            @PathVariable("surveyId") Long surveyId,
            @RequestBody @Valid SurveyDto.UpdateRequest requestDto
    ) {
        List<QuestionCommand> questionCommandList = requestDto.getQuestionList()
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
                requestDto.getTitle(),
                requestDto.getDescription(),
                questionCommandList
        );

        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/surveys")
    public ResponseEntity<ApiResponse<Page<SurveyDto.Response>>> getAllSurveys(Pageable pageable) {
        Page<SurveyCommand> surveyCommandPage = surveyService.getAllSurveys(pageable);
        Page<SurveyDto.Response> surveyResponsePage = surveyCommandPage.map(SurveyDto.Response::of);
        return ResponseEntity.ok(ApiResponse.ok(surveyResponsePage));
    }

    @GetMapping("/api/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<SurveyDto.Response>> getSurvey(
            @PathVariable("surveyId") Long surveyId
    ) {
        SurveyCommand surveyCommand = surveyService.findById(surveyId);
        return ResponseEntity.ok(ApiResponse.ok(SurveyDto.Response.of(surveyCommand)));
    }

    @GetMapping("/api/surveys/answer")
    public ResponseEntity<ApiResponse<Page<AnswerDto.Response>>> getSurveyAnswers(Pageable pageable) {
        Page<AnswerCommand> answerCommandPage = surveyService.getAllAnswers(pageable);
        Page<AnswerDto.Response> answerResponsePage = answerCommandPage.map(AnswerDto.Response::of);
        return ResponseEntity.ok(ApiResponse.ok(answerResponsePage));
    }

    // 고객용 api
    @GetMapping("/api/customers/surveys/{surveyId}")
    public ResponseEntity<ApiResponse<SurveyDto.Response>> getSurveyForCustomer(
            @PathVariable("surveyId") Long surveyId
    ) {
        SurveyCommand surveyCommand = surveyService.findById(surveyId);
        return ResponseEntity.ok(ApiResponse.ok(SurveyDto.Response.of(surveyCommand)));
    }

    @PostMapping("/api/customers/surveys/{surveyId}/submit")
    public ResponseEntity<ApiResponse<Void>> submitSurveyAnswer(
            @PathVariable("surveyId") Long surveyId,
            @RequestBody @Valid AnswerDto.Request requestDto
    ) {
        surveyService.submitSurveyAnswer(
                surveyId,
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPhone(),
                requestDto.getConsent(),
                requestDto.getQuestions(),
                requestDto.getAnswers()
        );
        return ResponseEntity.ok(ApiResponse.ok());
    }

}

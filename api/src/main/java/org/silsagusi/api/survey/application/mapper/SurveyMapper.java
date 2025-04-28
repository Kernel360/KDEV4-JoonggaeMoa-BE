package org.silsagusi.api.survey.application.mapper;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.api.survey.application.dto.QuestionDto;
import org.silsagusi.api.survey.application.dto.SurveyDto;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.survey.entity.Question;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.core.domain.survey.entity.Survey;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SurveyMapper {

	public Survey toSurvey(Agent agent, SurveyDto.CreateRequest createSurveyRequest) {
		return Survey.create(
			agent,
			createSurveyRequest.getTitle(),
			createSurveyRequest.getDescription(),
			new ArrayList<>()
		);
	}

	public List<Question> fromCreateDtoToQuestions(Survey survey,
		List<QuestionDto.CreateRequest> createQuestionCRequests) {
		return createQuestionCRequests.stream()
			.map(request ->
				Question.create(
					survey, request.getContent(), request.getType(), request.getIsRequired(), request.getOptions()
				)
			).toList();
	}

	public List<Question> fromUpdateDtoToQuestions(Survey survey,
		List<QuestionDto.UpdateRequest> updateQuestionCRequests) {
		return updateQuestionCRequests.stream()
			.map(request ->
				Question.create(
					survey, request.getContent(), request.getType(), request.getIsRequired(), request.getOptions()
				)
			).toList();
	}

	public List<QuestionAnswerPair> mapToQuestionAnswerPairList(List<String> questionList,
		List<List<String>> answerList) {
		List<QuestionAnswerPair> pairList = new ArrayList<>();
		for (int i = 0; i < questionList.size(); i++) {
			QuestionAnswerPair pair = QuestionAnswerPair.create(
				questionList.get(i),
				answerList.get(i)
			);
			pairList.add(pair);
		}
		return pairList;
	}
}
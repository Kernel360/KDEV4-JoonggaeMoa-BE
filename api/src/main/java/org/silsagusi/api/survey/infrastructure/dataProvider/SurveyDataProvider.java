package org.silsagusi.api.survey.infrastructure.dataProvider;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;
import org.silsagusi.api.survey.infrastructure.repository.AnswerRepository;
import org.silsagusi.api.survey.infrastructure.repository.QuestionRepository;
import org.silsagusi.api.survey.infrastructure.repository.SurveyRepository;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.silsagusi.core.domain.survey.entity.Question;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.core.domain.survey.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SurveyDataProvider {

	private final SurveyRepository surveyRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;

	public void createSurvey(Survey survey, List<Question> questionList) {
		surveyRepository.save(survey);
		questionRepository.saveAll(questionList);
	}

	public Page<Survey> getSurveyPageByAgent(Agent agent, Pageable pageable) {
		return surveyRepository.findAllByAgent(agent, pageable);
	}

	public Survey getSurvey(String surveyId) {
		return surveyRepository.findById(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public void updateSurvey(Survey survey, String title, String description, List<Question> questionList) {
		questionRepository.deleteAll(survey.getQuestionList());
		survey.getQuestionList().clear();

		survey.updateSurveyTitleDescription(
			(title == null || title.isBlank()) ? survey.getTitle() :
				title,
			(description == null || description.isBlank()) ?
				survey.getDescription() :
				description
		);

		survey.getQuestionList().addAll(questionList);
		questionRepository.saveAll(questionList);
		surveyRepository.save(survey);
	}

	public void deleteSurvey(Survey survey) {
		questionRepository.deleteAll(survey.getQuestionList());
		surveyRepository.delete(survey);
	}

	public void createAnswer(
		Boolean applyConsultation,
		LocalDateTime consultAt,
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairList
	) {
		Answer answer = new Answer(
			applyConsultation,
			consultAt,
			customer,
			survey,
			questionAnswerPairList
		);
		answerRepository.save(answer);
	}

	public Page<Answer> getAnswerPage(Long agentId, Pageable pageable) {
		return answerRepository.findAllByCustomer_AgentId(agentId, pageable);
	}

	public void validateSurveyWithAgent(Agent agent, Survey survey) {
		if (!survey.getAgent().equals(agent)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}

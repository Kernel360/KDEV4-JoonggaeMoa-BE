package org.silsagusi.api.survey.infrastructure.dataprovider;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
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
		return surveyRepository.findAllByAgentAndDeletedAtIsNull(agent, pageable);
	}

	public Survey getSurvey(String surveyId) {
		return surveyRepository.findByIdAndDeletedAtIsNull(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public void updateSurvey(Survey survey, String title, String description, List<Question> questionList) {
		questionRepository.deleteAll(survey.getQuestionList());
		survey.getQuestionList().clear();

		survey.updateSurveyTitleDescription(title, description);

		survey.getQuestionList().addAll(questionList);
		questionRepository.saveAll(questionList);
	}

	public void deleteSurvey(Survey survey) {
		survey.getQuestionList().forEach(Question::markAsDeleted);
		survey.markAsDeleted();
	}

	public void createAnswer(
		Boolean applyConsultation,
		LocalDateTime consultAt,
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairList
	) {
		Answer answer = Answer.create(
			applyConsultation,
			consultAt,
			customer,
			survey,
			questionAnswerPairList
		);
		answerRepository.save(answer);
	}

	public Page<Answer> getAnswerPage(Long agentId, Pageable pageable) {
		return answerRepository.findAllByCustomer_AgentIdAndDeletedAtIsNull(agentId, pageable);
	}
}

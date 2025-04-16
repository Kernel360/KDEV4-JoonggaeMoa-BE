package org.silsagusi.joonggaemoa.api.survey.application;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.joonggaemoa.core.domain.agent.Agent;
import org.silsagusi.joonggaemoa.core.domain.agent.AgentDataProvider;
import org.silsagusi.joonggaemoa.core.domain.consultation.dataProvider.ConsultationDataProvider;
import org.silsagusi.joonggaemoa.core.domain.consultation.entity.Consultation;
import org.silsagusi.joonggaemoa.core.domain.customer.dataProvider.CustomerDataProvider;
import org.silsagusi.joonggaemoa.core.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.core.domain.notification.dataProvider.NotificationDataProvider;
import org.silsagusi.joonggaemoa.core.domain.notification.entity.NotificationType;
import org.silsagusi.joonggaemoa.api.survey.application.dto.AnswerDto;
import org.silsagusi.joonggaemoa.api.survey.application.dto.SurveyDto;
import org.silsagusi.joonggaemoa.core.domain.survey.command.QuestionCommand;
import org.silsagusi.joonggaemoa.core.domain.survey.dataProvider.SurveyDataProvider;
import org.silsagusi.joonggaemoa.core.domain.survey.entity.Answer;
import org.silsagusi.joonggaemoa.core.domain.survey.entity.Question;
import org.silsagusi.joonggaemoa.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.joonggaemoa.core.domain.survey.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final AgentDataProvider agentDataProvider;
	private final SurveyDataProvider surveyDataProvider;
	private final CustomerDataProvider customerDataProvider;
	private final ConsultationDataProvider consultationDataProvider;
	private final NotificationDataProvider notificationDataProvider;

	@Transactional
	public void createSurvey(
		Long agentId,
		SurveyDto.CreateRequest surveyCreateRequest
	) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = new Survey(agent, surveyCreateRequest.getTitle(), surveyCreateRequest.getDescription(),
			new ArrayList<>());

		List<QuestionCommand> questionCommandList =
			surveyCreateRequest.getQuestionList().stream()
				.map(QuestionCommand::of)
				.toList();
		List<Question> questionList = surveyDataProvider.mapToQuestionList(survey, questionCommandList);

		surveyDataProvider.createSurvey(survey, questionList);
	}

	@Transactional
	public void deleteSurvey(Long agentId, String surveyId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		surveyDataProvider.validateSurveyWithAgent(agent, survey);
		surveyDataProvider.deleteSurvey(survey);
	}

	@Transactional
	public void updateSurvey(
		Long agentId,
		String surveyId,
		SurveyDto.UpdateRequest surveyUpdateRequest
	) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		surveyDataProvider.validateSurveyWithAgent(agent, survey);

		List<QuestionCommand> questionCommandList =
			surveyUpdateRequest.getQuestionList()
				.stream()
				.map(QuestionCommand::of)
				.toList();
		List<Question> questionList = surveyDataProvider.mapToQuestionList(survey, questionCommandList);

		surveyDataProvider.updateSurvey(
			survey,
			surveyUpdateRequest.getTitle(),
			surveyUpdateRequest.getDescription(),
			questionList
		);
	}

	@Transactional(readOnly = true)
	public Page<SurveyDto.Response> getAllSurveys(Long agentId, Pageable pageable) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Page<Survey> surveyPage = surveyDataProvider.getSurveyPageByAgent(agent, pageable);
		return surveyPage.map(SurveyDto.Response::of);
	}

	@Transactional(readOnly = true)
	public SurveyDto.Response findById(String surveyId) {
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		return SurveyDto.Response.of(survey);
	}

	@Transactional
	public void submitSurveyAnswer(
		String surveyId,
		AnswerDto.Request answerRequest
	) {
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		Agent agent = survey.getAgent();

		Customer customer = customerDataProvider.getCustomerByPhone(answerRequest.getPhone());
		if (customer == null) {
			customerDataProvider.createCustomer(
				answerRequest.getName(),
				null,
				answerRequest.getPhone(),
				answerRequest.getEmail(),
				null,
				null,
				null,
				answerRequest.getConsent(),
				agent
			);
		}

		if (answerRequest.getApplyConsultation()) {
			consultationDataProvider.createConsultation(
				customer,
				answerRequest.getConsultAt(),
				Consultation.ConsultationStatus.WAITING
			);
		}

		// 응답 추가
		List<QuestionAnswerPair> pairList = surveyDataProvider.mapToQuestionAnswerPairList(
			answerRequest.getQuestions(), answerRequest.getAnswers()
		);

		surveyDataProvider.createAnswer(
			answerRequest.getApplyConsultation(),
			answerRequest.getConsultAt(),
			customer,
			survey,
			pairList
		);

		//응답 완료 시 알림
		notificationDataProvider.notify(
			agent.getId(),
			NotificationType.SURVEY,
			customer.getName() + "님이 [" + survey.getTitle() + "] 설문에 응답했습니다."
		);
	}

	public Page<AnswerDto.Response> getAllAnswers(Long agentId, Pageable pageable) {
		Page<Answer> answerPage = surveyDataProvider.getAnswerPage(agentId, pageable);
		return answerPage.map(AnswerDto.Response::of);
	}
}

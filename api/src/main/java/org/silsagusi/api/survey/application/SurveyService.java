package org.silsagusi.api.survey.application;

import java.util.List;

import org.silsagusi.api.agent.infrastructure.AgentDataProvider;
import org.silsagusi.api.consultation.infrastructure.ConsultationDataProvider;
import org.silsagusi.api.customer.application.CustomerMapper;
import org.silsagusi.api.customer.infrastructure.CustomerDataProvider;
import org.silsagusi.api.notify.infrastructure.NotificationDataProvider;
import org.silsagusi.api.survey.application.dto.AnswerDto;
import org.silsagusi.api.survey.application.dto.SurveyDto;
import org.silsagusi.api.survey.application.mapper.SurveyMapper;
import org.silsagusi.api.survey.infrastructure.dataProvider.SurveyDataProvider;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.consultation.entity.Consultation;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.silsagusi.core.domain.survey.entity.Question;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.core.domain.survey.entity.Survey;
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
	private final CustomerMapper customerMapper;
	private final SurveyMapper surveyMapper;

	@Transactional
	public void createSurvey(Long agentId, SurveyDto.CreateRequest surveyCreateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyMapper.toSurvey(agent, surveyCreateRequest);
		List<Question> questions = surveyMapper.fromCreateDtoToQuestions(survey, surveyCreateRequest.getQuestionList());

		surveyDataProvider.createSurvey(survey, questions);
	}

	@Transactional
	public void deleteSurvey(Long agentId, String surveyId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);

		surveyDataProvider.validateSurveyWithAgent(agent, survey);

		surveyDataProvider.deleteSurvey(survey);
	}

	@Transactional
	public void updateSurvey(Long agentId, String surveyId, SurveyDto.UpdateRequest surveyUpdateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		surveyDataProvider.validateSurveyWithAgent(agent, survey);

		List<Question> questions = surveyMapper.fromUpdateDtoToQuestions(survey, surveyUpdateRequest.getQuestionList());

		surveyDataProvider.updateSurvey(
			survey,
			surveyUpdateRequest.getTitle(),
			surveyUpdateRequest.getDescription(),
			questions
		);
	}

	@Transactional(readOnly = true)
	public Page<SurveyDto.Response> getAllSurveys(Long agentId, Pageable pageable) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Page<Survey> surveyPage = surveyDataProvider.getSurveyPageByAgent(agent, pageable);
		return surveyPage.map(surveyMapper::toSurveyResponse);
	}

	@Transactional(readOnly = true)
	public SurveyDto.Response findById(String surveyId) {
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		return surveyMapper.toSurveyResponse(survey);
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
			Customer newCustomer = customerMapper.answerDtoToCustomer(answerRequest, agent);
			customerDataProvider.createCustomer(newCustomer);
		}

		if (Boolean.TRUE.equals(answerRequest.getApplyConsultation())) {
			consultationDataProvider.createConsultation(
				customer,
				answerRequest.getConsultAt(),
				Consultation.ConsultationStatus.WAITING
			);
		}

		// 응답 추가
		List<QuestionAnswerPair> pairList = surveyMapper.mapToQuestionAnswerPairList(
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

		return answerPage.map(surveyMapper::toAnswerResponse);
	}
}

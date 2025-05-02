package org.silsagusi.api.survey.application.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.silsagusi.api.agent.infrastructure.dataprovider.AgentDataProvider;
import org.silsagusi.api.consultation.application.mapper.ConsultationMapper;
import org.silsagusi.api.consultation.infrastructure.dataprovider.ConsultationDataProvider;
import org.silsagusi.api.customer.application.mapper.CustomerMapper;
import org.silsagusi.api.customer.infrastructure.dataprovider.CustomerDataProvider;
import org.silsagusi.api.notification.infrastructure.dataprovider.NotificationDataProvider;
import org.silsagusi.api.survey.application.dto.AnswerResponse;
import org.silsagusi.api.survey.application.dto.CreateSurveyRequest;
import org.silsagusi.api.survey.application.dto.SubmitAnswerRequest;
import org.silsagusi.api.survey.application.dto.SurveyDetailResponse;
import org.silsagusi.api.survey.application.dto.SurveyResponse;
import org.silsagusi.api.survey.application.dto.UpdateSurveyRequest;
import org.silsagusi.api.survey.application.mapper.SurveyMapper;
import org.silsagusi.api.survey.application.validator.SurveyValidator;
import org.silsagusi.api.survey.infrastructure.dataprovider.SurveyDataProvider;
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
	private final SurveyValidator surveyValidator;
	private final ConsultationMapper consultationMapper;

	@Transactional
	public void createSurvey(Long agentId, CreateSurveyRequest surveyCreateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyMapper.toSurvey(agent, surveyCreateRequest);
		List<Question> questions = surveyMapper.fromCreateDtoToQuestions(survey, surveyCreateRequest.getQuestionList());

		surveyDataProvider.createSurvey(survey, questions);
	}

	@Transactional
	public void deleteSurvey(Long agentId, String surveyId) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);

		surveyValidator.validateAgentAccess(agent, survey);

		surveyDataProvider.deleteSurvey(survey);
	}

	@Transactional
	public void updateSurvey(Long agentId, String surveyId, UpdateSurveyRequest surveyUpdateRequest) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		surveyValidator.validateAgentAccess(agent, survey);

		List<Question> questions = surveyMapper.fromUpdateDtoToQuestions(survey, surveyUpdateRequest.getQuestionList());

		surveyDataProvider.updateSurvey(
			survey,
			surveyUpdateRequest.getTitle(),
			surveyUpdateRequest.getDescription(),
			questions
		);
	}

	@Transactional(readOnly = true)
	public Page<SurveyResponse> getAllSurveys(Long agentId, Pageable pageable) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Page<Survey> surveyPage = surveyDataProvider.getSurveyPageByAgent(agent, pageable);
		return surveyPage.map(SurveyResponse::toResponse);
	}

	@Transactional(readOnly = true)
	public SurveyDetailResponse findById(String surveyId) {
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		return SurveyDetailResponse.toResponse(survey);
	}

	@Transactional
	public void submitSurveyAnswer(
		String surveyId,
		SubmitAnswerRequest submitAnswerRequest
	) {
		Survey survey = surveyDataProvider.getSurvey(surveyId);
		Agent agent = survey.getAgent();

		Customer customer = customerDataProvider.getCustomerByPhone(submitAnswerRequest.getPhone());
		if (customer == null) {
			customer = customerMapper.toEntity(submitAnswerRequest, agent);
			customerDataProvider.createCustomer(customer);
		}

		if (Boolean.TRUE.equals(submitAnswerRequest.getApplyConsultation())) {
			Consultation consultation = consultationMapper.answerRequestToEntity(customer,
				submitAnswerRequest.getConsultAt());
			consultationDataProvider.createConsultation(consultation);

			//상담 신청 시 알림
			notificationDataProvider.notify(
				agent.getId(),
				NotificationType.CONSULTATION,
				customer.getName() + "님이 [" + consultation.getDate()
					.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")) + "] 시에 상담을 신청했습니다."
			);
		}

		// 응답 추가
		List<QuestionAnswerPair> pairList = surveyMapper.mapToQuestionAnswerPairList(
			submitAnswerRequest.getQuestions(), submitAnswerRequest.getAnswers()
		);

		surveyDataProvider.createAnswer(
			submitAnswerRequest.getApplyConsultation(),
			submitAnswerRequest.getConsultAt(),
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

	@Transactional(readOnly = true)
	public Page<AnswerResponse> getAllAnswers(Long agentId, Pageable pageable) {
		Page<Answer> answerPage = surveyDataProvider.getAnswerPage(agentId, pageable);

		return answerPage.map(AnswerResponse::toResponse);
	}
}

package org.silsagusi.joonggaemoa.api.survey.application;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.agent.domain.AgentDataProvider;
import org.silsagusi.joonggaemoa.api.survey.application.dto.AnswerDto;
import org.silsagusi.joonggaemoa.api.survey.application.dto.SurveyDto;
import org.silsagusi.joonggaemoa.api.survey.domain.command.QuestionCommand;
import org.silsagusi.joonggaemoa.api.survey.domain.dataProvider.SurveyDataProvider;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Answer;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Question;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.QuestionAnswerPair;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Survey;
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

	@Transactional
	public void createSurvey(
		Long agentId,
		SurveyDto.CreateRequest surveyCreateRequest
	) {
		Agent agent = agentDataProvider.getAgentById(agentId);
		Survey survey = new Survey(agent, surveyCreateRequest.getTitle(), surveyCreateRequest.getDescription(),
			new ArrayList<>());

		List<QuestionCommand> questionCommandList = QuestionCommand.fromCreateRequest(
			surveyCreateRequest.getQuestionList());
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

		List<QuestionCommand> questionCommandList = QuestionCommand.fromUpdateRequest(
			surveyUpdateRequest.getQuestionList());
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

		// 고객인지 판별(휴대폰 번호) 후 고객 데이터 추가
		// Customer customer = customerService.getCustomerByPhone(answerRequest.getPhone());
		// if (customer == null) {
		// 	Customer newCustomer = new Customer(
		// 		answerRequest.getName(),
		// 		answerRequest.getPhone(),
		// 		answerRequest.getEmail(),
		// 		answerRequest.getConsent(),
		// 		agent
		// 	);
		// 	customerRepository.save(newCustomer);
		// 	customer = newCustomer;
		// }
		// TODO 고객 검증 및 추가

		// if (answerRequest.getApplyConsultation()) {
		// 	// 상담 추가
		// 	Consultation consultation = new Consultation(
		// 		customer,
		// 		answerRequest.getConsultAt(),
		// 		Consultation.ConsultationStatus.WAITING
		// 	);
		// 	consultationRepository.save(consultation);
		// }
		// TODO 상담 추가

		// 응답 추가
		List<QuestionAnswerPair> pairList = surveyDataProvider.mapToQuestionAnswerPairList(
			answerRequest.getQuestions(), answerRequest.getAnswers()
		);

		Answer answer = new Answer(
			answerRequest.getApplyConsultation(),
			answerRequest.getConsultAt(),
			null, // customer,
			survey,
			pairList
		);

		surveyDataProvider.createAnswer(answer);

		//응답 완료 시 알림
		// notificationService.notify(
		// 	agent.getId(),
		// 	NotificationType.SURVEY,
		// 	customer.getName() + "님이 [" + survey.getTitle() + "] 설문에 응답했습니다."
		// );
		// TODO 알림 추가
	}

	public Page<AnswerDto.Response> getAllAnswers(Long agentId, Pageable pageable) {
		Page<Answer> answerPage = surveyDataProvider.getAnswerPage(agentId, pageable);
		return answerPage.map(AnswerDto.Response::of);
	}
}

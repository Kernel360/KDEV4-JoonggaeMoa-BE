package org.silsagusi.joonggaemoa.domain.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.consultation.entity.Consultation;
import org.silsagusi.joonggaemoa.domain.consultation.repository.ConsultationRepository;
import org.silsagusi.joonggaemoa.domain.customer.entity.Customer;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.customer.service.CustomerService;
import org.silsagusi.joonggaemoa.domain.notify.entity.NotificationType;
import org.silsagusi.joonggaemoa.domain.notify.service.NotificationService;
import org.silsagusi.joonggaemoa.domain.survey.entity.Answer;
import org.silsagusi.joonggaemoa.domain.survey.entity.Question;
import org.silsagusi.joonggaemoa.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.joonggaemoa.domain.survey.entity.Survey;
import org.silsagusi.joonggaemoa.domain.survey.repository.AnswerRepository;
import org.silsagusi.joonggaemoa.domain.survey.repository.QuestionRepository;
import org.silsagusi.joonggaemoa.domain.survey.repository.SurveyRepository;
import org.silsagusi.joonggaemoa.domain.survey.service.dto.AnswerDto;
import org.silsagusi.joonggaemoa.domain.survey.service.dto.SurveyDto;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final SurveyRepository surveyRepository;
	private final AgentRepository agentRepository;
	private final QuestionRepository questionRepository;
	private final CustomerService customerService;
	private final CustomerRepository customerRepository;
	private final AnswerRepository answerRepository;
	private final ConsultationRepository consultationRepository;
	private final NotificationService notificationService;

	@Transactional
	public void createSurvey(
		Long agentId,
		SurveyDto.CreateRequest surveyCreateRequest
	) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Survey survey = new Survey(agent, surveyCreateRequest.getTitle(), surveyCreateRequest.getDescription(),
			new ArrayList<>());

		List<Question> questionList = surveyCreateRequest.getQuestionList().stream()
			.map(
				it -> {
					Question question = new Question(
						survey,
						it.getContent(),
						it.getType(),
						it.getIsRequired(),
						it.getOptions()
					);
					survey.getQuestionList().add(question);
					return question;
				}
			).toList();

		surveyRepository.save(survey);
		questionRepository.saveAll(questionList);

	}

	@Transactional
	public void deleteSurvey(Long agentId, String surveyId) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!survey.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}

		questionRepository.deleteAll(survey.getQuestionList());
		surveyRepository.delete(survey);
	}

	@Transactional
	public void updateSurvey(
		Long agentId,
		String surveyId,
		SurveyDto.UpdateRequest surveyUpdateRequest
	) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));

		if (!survey.getAgent().getId().equals(agentId)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
		survey.updateSurveyTitleDescription(
			(surveyUpdateRequest.getTitle() == null || surveyUpdateRequest.getTitle().isBlank()) ? survey.getTitle() :
				surveyUpdateRequest.getTitle(),
			(surveyUpdateRequest.getDescription() == null || surveyUpdateRequest.getDescription().isBlank()) ?
				survey.getDescription() :
				surveyUpdateRequest.getDescription()
		);

		questionRepository.deleteAll(survey.getQuestionList());
		survey.getQuestionList().clear();

		List<Question> updateQuestions = surveyUpdateRequest.getQuestionList().stream()
			.map(it -> new Question(
					survey,
					it.getContent(),
					it.getType(),
					it.getIsRequired(),
					it.getOptions()
				)
			).toList();

		survey.getQuestionList().addAll(updateQuestions);
		questionRepository.saveAll(updateQuestions);
		surveyRepository.save(survey);

	}

	@Transactional(readOnly = true)
	public Page<SurveyDto.Response> getAllSurveys(Long agentId, Pageable pageable) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

		Page<Survey> surveyPage = surveyRepository.findAllByAgent(agent, pageable);
		return surveyPage.map(SurveyDto.Response::of);
	}

	@Transactional(readOnly = true)
	public SurveyDto.Response findById(String surveyId) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		return SurveyDto.Response.of(survey);
	}

	@Transactional
	public void submitSurveyAnswer(
		String surveyId,
		AnswerDto.Request answerRequest
	) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
		Agent agent = survey.getAgent();

		// 고객인지 판별(휴대폰 번호) 후 고객 데이터 추가
		Customer customer = customerService.getCustomerByPhone(answerRequest.getPhone());
		if (customer == null) {
			Customer newCustomer = new Customer(
				answerRequest.getName(),
				answerRequest.getPhone(),
				answerRequest.getEmail(),
				answerRequest.getConsent(),
				agent
			);
			customerRepository.save(newCustomer);
			customer = newCustomer;
		}

		if (answerRequest.getApplyConsultation()) {
			// 상담 추가
			Consultation consultation = new Consultation(
				customer,
				answerRequest.getConsultAt(),
				Consultation.ConsultationStatus.WAITING
			);
			consultationRepository.save(consultation);
		}

		// 응답 추가
		List<QuestionAnswerPair> pairList = new ArrayList<>();
		for (int i = 0; i < answerRequest.getQuestions().size(); i++) {
			QuestionAnswerPair pair = new QuestionAnswerPair(
				answerRequest.getQuestions().get(i),
				answerRequest.getAnswers().get(i)
			);
			pairList.add(pair);
		}

		Answer newAnswer = new Answer(
			answerRequest.getApplyConsultation(),
			answerRequest.getConsultAt(),
			customer,
			survey,
			pairList
		);

		answerRepository.save(newAnswer);

		//응답 완료 시 알림
		notificationService.notify(
			agent.getId(),
			NotificationType.SURVEY,
			customer.getName() + "님이 [" + survey.getTitle() + "] 설문에 응답했습니다."
		);
	}

	public Page<AnswerDto.Response> getAllAnswers(Long agentId, Pageable pageable) {
		Page<Answer> answerPage = answerRepository.findAllByCustomer_AgentId(agentId, pageable);
		return answerPage.map(AnswerDto.Response::of);
	}
}

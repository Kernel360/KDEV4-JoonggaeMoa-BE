package org.silsagusi.joonggaemoa.domain.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.agent.entity.Agent;
import org.silsagusi.joonggaemoa.domain.agent.repository.AgentRepository;
import org.silsagusi.joonggaemoa.domain.survey.entity.Question;
import org.silsagusi.joonggaemoa.domain.survey.entity.Survey;
import org.silsagusi.joonggaemoa.domain.survey.repository.QuestionRepository;
import org.silsagusi.joonggaemoa.domain.survey.repository.SurveyRepository;
import org.silsagusi.joonggaemoa.domain.survey.service.command.QuestionCommand;
import org.silsagusi.joonggaemoa.domain.survey.service.command.SurveyCommand;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {

	private final SurveyRepository surveyRepository;
	private final AgentRepository agentRepository;
	private final QuestionRepository questionRepository;

	public void createSurvey(
		Long agentId,
		String title,
		String description,
		List<QuestionCommand> questionCommandList
	) {
		Agent agent = agentRepository.getOne(agentId);

		Survey survey = new Survey(agent, title, description, new ArrayList<>());

		List<Question> questionList = questionCommandList.stream()
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
	public void deleteSurvey(Long surveyId) {
		questionRepository.deleteBySurvey_Id(surveyId);
		surveyRepository.deleteById(surveyId);
	}

	public void updateSurvey(
		Long surveyId,
		String title,
		String description,
		List<QuestionCommand> questionCommandList
	) {
		Survey survey = surveyRepository.findById(surveyId)
			.orElseThrow(() -> new EntityNotFoundException("Survey not found"));

		survey.updateSurveyTitleDescription(
			(title == null || title.isBlank()) ? survey.getTitle() : title,
			(description == null || description.isBlank()) ? survey.getDescription() : description
		);

		List<Question> questionList = survey.getQuestionList();
		List<Question> questionsToRemove = new ArrayList<>();
		List<Question> questionsToAdd = new ArrayList<>();

		//바뀐 질문, 새로운 질문
		for (QuestionCommand command : questionCommandList) {
			//새로운 질문
			if (command.getId() < 0 || command.getId() == null) {
				Question question = new Question(
					survey,
					command.getContent(),
					command.getType(),
					command.getIsRequired(),
					command.getOptions()
				);
				survey.getQuestionList().add(question);
			} else {
				//바뀐 질문
				for (Question question : questionList) {
					if (question.getId().equals(command.getId())) {
						question.updateQuestion(
							command.getContent(),
							command.getType(),
							command.getIsRequired(),
							command.getOptions()
						);
						break;
					}
				}
			}

		}

		//삭제 된 질문
		for (Question question : questionList) {
			boolean found = false;
			for (QuestionCommand command : questionCommandList) {
				if (question.getId().equals(command.getId())) {
					found = true;
					break;
				}
			}
			if (!found)
				questionsToRemove.add(question);
		}

		questionRepository.deleteAll(questionsToRemove);
		questionRepository.saveAll(questionList);
		surveyRepository.save(survey);
	}

	public List<SurveyCommand> getAllSurveys() {
		List<Survey> surveyList = surveyRepository.findAll();
		return surveyList.stream().map(it -> SurveyCommand.of(it)).toList();
	}

	public SurveyCommand findById(Long surveyId) {
		Survey survey = surveyRepository.getOne(surveyId);
		return SurveyCommand.of(survey);
	}

}

package org.silsagusi.joonggaemoa.api.survey.domain.dataProvider;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.api.agent.domain.Agent;
import org.silsagusi.joonggaemoa.api.customer.domain.entity.Customer;
import org.silsagusi.joonggaemoa.api.survey.domain.command.QuestionCommand;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Answer;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Question;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.QuestionAnswerPair;
import org.silsagusi.joonggaemoa.api.survey.domain.entity.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyDataProvider {

	void createSurvey(Survey survey, List<Question> questionList);

	List<Question> mapToQuestionList(Survey survey, List<QuestionCommand> questionCommandList);

	List<QuestionAnswerPair> mapToQuestionAnswerPairList(List<String> questionList, List<List<String>> answerList);

	Page<Survey> getSurveyPageByAgent(Agent agent, Pageable pageable);

	Survey getSurvey(String surveyId);

	void updateSurvey(Survey survey, String title, String description, List<Question> questions);

	void deleteSurvey(Survey survey);

	void createAnswer(
		Boolean applyConsultation,
		LocalDateTime consultAt,
		Customer customer,
		Survey survey,
		List<QuestionAnswerPair> questionAnswerPairs
	);

	Page<Answer> getAnswerPage(Long agentId, Pageable pageable);

	void validateSurveyWithAgent(Agent agent, Survey survey);
}

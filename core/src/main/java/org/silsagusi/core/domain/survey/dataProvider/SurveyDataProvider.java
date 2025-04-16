package org.silsagusi.core.domain.survey.dataProvider;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.customer.entity.Customer;
import org.silsagusi.core.domain.survey.command.QuestionCommand;
import org.silsagusi.core.domain.survey.entity.Answer;
import org.silsagusi.core.domain.survey.entity.Question;
import org.silsagusi.core.domain.survey.entity.QuestionAnswerPair;
import org.silsagusi.core.domain.survey.entity.Survey;
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

package org.silsagusi.joonggaemoa.domain.survey.service.command;

import java.util.List;

import org.silsagusi.joonggaemoa.domain.customer.service.command.CustomerCommand;
import org.silsagusi.joonggaemoa.domain.survey.entity.Answer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerCommand {
	private CustomerCommand customer;
	private SurveyCommand survey;
	private List<QuestionAnswerCommand> answers;

	public static AnswerCommand of(Answer answer) {
		List<QuestionAnswerCommand> questionAnswerCommandList = answer.getAnswer().stream()
			.map(it -> QuestionAnswerCommand.of(it)).toList();

		return AnswerCommand.builder()
			.customer(CustomerCommand.of(answer.getCustomer()))
			.survey(SurveyCommand.of(answer.getSurvey()))
			.answers(questionAnswerCommandList)
			.build();
	}
}

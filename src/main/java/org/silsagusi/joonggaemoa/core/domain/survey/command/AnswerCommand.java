package org.silsagusi.joonggaemoa.core.domain.survey.command;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerCommand {
	private List<String> questionList;
	private List<List<String>> answerList;
}

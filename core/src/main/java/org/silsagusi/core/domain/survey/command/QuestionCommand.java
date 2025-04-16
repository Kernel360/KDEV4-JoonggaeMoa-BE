package org.silsagusi.core.domain.survey.command;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCommand {
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;
}

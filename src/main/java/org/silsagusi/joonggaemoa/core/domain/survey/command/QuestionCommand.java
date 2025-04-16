package org.silsagusi.joonggaemoa.core.domain.survey.command;

import java.util.List;

import org.silsagusi.joonggaemoa.api.survey.application.dto.QuestionDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionCommand {
	private String content;
	private String type;
	private Boolean isRequired;
	private List<String> options;

	public static QuestionCommand of(QuestionDto.CreateRequest questionDto) {
		return QuestionCommand.builder()
			.content(questionDto.getContent())
			.type(questionDto.getType())
			.isRequired(questionDto.getIsRequired())
			.options(questionDto.getOptions())
			.build();
	}

	public static QuestionCommand of(QuestionDto.UpdateRequest questionDto) {
		return QuestionCommand.builder()
			.content(questionDto.getContent())
			.type(questionDto.getType())
			.isRequired(questionDto.getIsRequired())
			.options(questionDto.getOptions())
			.build();
	}
}

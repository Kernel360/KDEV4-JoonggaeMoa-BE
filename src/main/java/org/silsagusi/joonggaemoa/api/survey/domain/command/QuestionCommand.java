package org.silsagusi.joonggaemoa.api.survey.domain.command;

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

	public static List<QuestionCommand> fromCreateRequest(List<QuestionDto.CreateRequest> questionDtoList) {
		return questionDtoList.stream()
			.map(questionDto ->
				QuestionCommand.builder()
					.content(questionDto.getContent())
					.type(questionDto.getType())
					.isRequired(questionDto.getIsRequired())
					.options(questionDto.getOptions())
					.build()
			)
			.toList();
	}

	public static List<QuestionCommand> fromUpdateRequest(List<QuestionDto.UpdateRequest> questionDtoList) {
		return questionDtoList.stream()
			.map(questionDto ->
				QuestionCommand.builder()
					.content(questionDto.getContent())
					.type(questionDto.getType())
					.isRequired(questionDto.getIsRequired())
					.options(questionDto.getOptions())
					.build()
			)
			.toList();
	}
}

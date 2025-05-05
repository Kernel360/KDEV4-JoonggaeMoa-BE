package org.silsagusi.api.message.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.message.command.UpdateMessageCommand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMessageRequest {

	private String content;
	private LocalDateTime sendAt;

	public UpdateMessageCommand toCommand() {
		return new UpdateMessageCommand(content, sendAt);
	}
}

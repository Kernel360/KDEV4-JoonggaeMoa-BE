package org.silsagusi.core.domain.message.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMessageCommand {
	private String content;
	private LocalDateTime sendAt;
}

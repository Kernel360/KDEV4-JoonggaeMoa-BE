package org.silsagusi.core.domain.message.command;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateMessageCommand {
	private String content;
	private LocalDateTime sendAt;
}

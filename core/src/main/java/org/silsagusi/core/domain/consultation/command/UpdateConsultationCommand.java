package org.silsagusi.core.domain.consultation.command;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateConsultationCommand {
	private LocalDateTime date;
	private String purpose;
	private String memo;
}

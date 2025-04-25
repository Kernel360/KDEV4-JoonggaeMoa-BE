package org.silsagusi.api.consultation.application.dto;

import java.time.LocalDateTime;

import org.silsagusi.core.domain.consultation.command.UpdateConsultationCommand;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateConsultationRequest {

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime date;
	private String purpose;
	private String memo;

	public static UpdateConsultationCommand toCommand(UpdateConsultationRequest updateConsultationRequest) {
		return UpdateConsultationCommand.builder()
			.date(updateConsultationRequest.getDate())
			.purpose(updateConsultationRequest.getPurpose())
			.memo(updateConsultationRequest.getMemo())
			.build();
	}
}
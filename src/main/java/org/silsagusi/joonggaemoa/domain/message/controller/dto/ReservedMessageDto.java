package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReservedMessageDto {

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		@NotBlank(message = "내용은 필수 값입니다.")
		private String content;

		@NotNull
		@FutureOrPresent
		@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
		private LocalDateTime sendAt;

		private List<@NotNull Long> customerIdList;
	}

	@Getter
	@Builder
	public static class Response {
		private Long id;
		private LocalDateTime sendAt;
		private Long customerId;
		private String customerName;
		private String customerPhone;
		private String content;

		public static Response of(ReservedMessageCommand command) {
			return Response.builder()
				.id(command.getId())
				.sendAt(command.getSendAt())
				.customerId(command.getCustomerId())
				.customerName(command.getCustomerName())
				.customerPhone(command.getCustomerPhone())
				.content(command.getContent())
				.build();
		}
	}
}

package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservedMessageUpdateRequest {

	private String content;
	private LocalDateTime sendAt;
}

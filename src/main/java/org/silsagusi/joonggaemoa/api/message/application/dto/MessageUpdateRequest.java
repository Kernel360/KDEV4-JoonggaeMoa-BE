package org.silsagusi.joonggaemoa.api.message.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateRequest {

	private String content;
	private LocalDateTime sendAt;
}

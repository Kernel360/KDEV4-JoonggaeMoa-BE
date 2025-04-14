package org.silsagusi.joonggaemoa.domain.message.service.dto;

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

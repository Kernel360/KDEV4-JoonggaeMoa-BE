package org.silsagusi.joonggaemoa.domain.message.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {

	private String content;
	private String sendAt;
	private List<Long> customerIdList;

}

package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindUsernameRequest {

	private String name;
	private String phone;
}

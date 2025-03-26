package org.silsagusi.joonggaemoa.domain.agent.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

	private String username;
	private String password;
	private String name;
	private String phone;
	private String email;
	private String office;
	private String region;
	private String businessNo;
}

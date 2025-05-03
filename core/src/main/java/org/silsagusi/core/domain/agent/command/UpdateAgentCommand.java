package org.silsagusi.core.domain.agent.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateAgentCommand {
	private String username;
	private String name;
	private String phone;
	private String email;
	private String office;
	private String region;
	private String businessNo;
}

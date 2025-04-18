package org.silsagusi.api.agent.exception;

import org.silsagusi.api.customResponse.exception.CustomException;
import org.silsagusi.api.customResponse.exception.ErrorCode;

public class AgentNotFoundException extends CustomException {
	public AgentNotFoundException(Long agentId) {
		super(ErrorCode.NOT_FOUND_USER, "ID : " + agentId);
	}

	public AgentNotFoundException(String name) {
		super(ErrorCode.NOT_FOUND_USER, "이름 : " + name);
	}
}

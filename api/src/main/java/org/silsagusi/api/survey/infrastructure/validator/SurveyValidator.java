package org.silsagusi.api.survey.infrastructure.validator;

import org.silsagusi.core.customResponse.exception.CustomException;
import org.silsagusi.core.customResponse.exception.ErrorCode;
import org.silsagusi.core.domain.agent.Agent;
import org.silsagusi.core.domain.survey.entity.Survey;
import org.springframework.stereotype.Component;

@Component
public class SurveyValidator {

	public void validateAgentAccess(Agent agent, Survey survey) {
		if (!survey.getAgent().equals(agent)) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
	}
}

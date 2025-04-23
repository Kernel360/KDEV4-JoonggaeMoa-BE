package org.silsagusi.api.survey.infrastructure.validator;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
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

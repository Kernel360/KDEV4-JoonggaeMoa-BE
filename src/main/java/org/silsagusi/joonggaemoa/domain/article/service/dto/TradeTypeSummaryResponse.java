package org.silsagusi.joonggaemoa.domain.article.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeTypeSummaryResponse {

	private String type;
	private Double ratio;
}
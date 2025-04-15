package org.silsagusi.joonggaemoa.api.article.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeTypeSummaryResponse {

	private String type;
	private Double ratio;
}
package org.silsagusi.api.article.application.dto;

import lombok.Getter;

@Getter
public class ClusterSummaryResponse {
	private final double latGroup;
	private final double lngGroup;
	private final long count;

	public ClusterSummaryResponse(
		double latGroup, double lngGroup, long count
	) {
		this.latGroup = latGroup;
		this.lngGroup = lngGroup;
		this.count = count;
	}
}
package org.silsagusi.batch.naverland.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NaverLandScrapeRequest {
	private final Long scrapeStatusId;
	private final Long regionId;
	private final String cortarNo;
	private final Double centerLat;
	private final Double centerLon;
	private final int lastScrapedPage;
}

package org.silsagusi.batch.zigbang.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ZigBangScrapeRequest {
	private final Long scrapeStatusId;
	private final Long regionId;
	private final String cortarNo;
	private final Integer lastScrapedPage;
	private final String geohash;
}

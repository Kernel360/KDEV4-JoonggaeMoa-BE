package org.silsagusi.batch.scrape.zigbang.service.dto;

import lombok.Data;

@Data
public class ZigBangLocalCodeResponse {
	private String code;
	private String name;
	private String geojson;
	private Boolean isPartnerArea;
	private Boolean isItemRequest;
	private Boolean isLocalItems;
}

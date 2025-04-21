package org.silsagusi.batch.scrape.zigbang.service.dto;

import lombok.Data;

import java.util.List;

public class ZigBangItemCatalogResponse {
	private List<ZigBangItemCatalog> filtered;

	@Data
	private static class ZigBangItemCatalog {
		private Integer id;
		private String name;
		private Double lat;
		private Double lng;
		private Integer totalHouseholds; // 총세대수
		private String approvalDate; // 사용승인일
		private String serviceType; // 서비스구분
		private String realType; // real_type
		private String sido;
		private String gugun;
		private String dong;
		private Boolean isNewStay;
		private String imageUrl;
		private Boolean isPostSale; // is후분양
		private List<Price> price;
	}

	@Data
	public static class Price {
		private List<Sales> sales;
		private List<Offer> offer;
	}

	@Data
	public static class Sales {
		private Integer min;
		private Integer max;
		private Integer avg;
		private Integer perArea;
	}

	@Data
	public static class Offer {
		private Integer min;
		private Integer max;
		private Integer avg;
		private Integer perArea;
	}
}

package org.silsagusi.batch.scrape.zigbang.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ZigBangDanjiResponse {
	private List<ZigBangDanji> filtered;
	private List<String> unfiltered;

	@Data
	public static class ZigBangDanji {
		private Integer id;
		private String name;
		private Double lat;
		private Double lng;
		private Integer 총세대수;
		@JsonFormat(pattern = "yyyyMMdd")
		private LocalDate 사용승인일;
		private String 서비스구분;
		private String real_type;
		private String sido;
		private String gugun;
		private String dong;
		private Boolean isNewStay;
		private String image;
//		private List<String> view_sources;
//		private Boolean is후분양;
		private Price price;
	}

	@Data
	public static class Price {
		private Sales sales;
		private Offer offer;
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

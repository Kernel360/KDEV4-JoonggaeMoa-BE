package org.silsagusi.batch.zigbang.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ZigBangDanjiResponse {
	private List<ZigBangDanji> filtered;
	private List<Map<String, Object>> unfiltered;

	@Data
	@ToString
	public static class ZigBangDanji {
		private Integer id;         // 단지 고유 번호
		private String name;        // 단지명
		private Double lat;         // 단지 위도
		private Double lng;         // 단지 경도
		private Integer 총세대수;
		@JsonFormat(pattern = "yyyyMMdd")
		private LocalDate 사용승인일;
		private String 서비스구분;
		private String real_type;   // 단지 분류 (전부 "아파트")
		private String sido;
		private String gugun;
		private String dong;
		private Boolean isNewStay;  // 신축 여부
		private String image;       // 이미지 경로
		//private List<String> view_sources; // 직방
		//private Boolean is후분양;
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

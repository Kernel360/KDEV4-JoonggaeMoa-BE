package org.silsagusi.batch.scrape.naverland.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NaverLandComplexResponse {
	private List<NaverLandComplex> result;
	private boolean hasPaidPreSale;
	private boolean more;
	private boolean isPreSale;

	// 네이버 부동산 단지 정보 API 응답값 형식
	@Data
	public static class NaverLandComplex {
		private String hscpNo; // 단지 고유 번호
		private String hscpNm; // 단지 이름
		private String hscpTypeCd; // 단지 유형 코드 (예: “A01”은 아파트)
		private String hscpTypeNm; // 단지 유형 이름 (예: “아파트”, “오피스텔”)
		private Integer totDongCnt; // 총 동 수
		private Integer totHsehCnt; // 총 세대 수
		private Integer genHsehCnt; // 일반 세대 수 (일반적으로 0으로 표시됨)
		@JsonFormat(pattern = "yyyy.MM.dd.")
		@JsonDeserialize(using = FlexibleDateDeserializer.class)
		private LocalDate useAprvYmd; // 사용 승인일 (예: “2025.02.”)
		private Integer dealCnt; // 매매 건수
		private Integer leaseCnt; // 전세 건수
		private Integer rentCnt; // 월세 건수
		private Integer strmRentCnt; // 단기 임대 건수
		private Integer totalAtclCnt; // 총 매물 수
		private String minSpc; // 최소 전용면적 (㎡)
		private String maxSpc; // 최대 전용면적 (㎡)
		private String isalePrcMin; // 최소 분양가 (일반적으로 0으로 표시됨)
		private String isalePrcMax; // 최대 분양가 (일반적으로 0으로 표시됨)
		private String isaleNotifSeq; // 분양 공고 순번 (일반적으로 0으로 표시됨)
		private String isaleScheLabel; // 분양 일정 라벨 (일반적으로 빈 문자열)
		private String isaleScheLabelPre; // 분양 일정 이전 라벨 (일반적으로 빈 문자열)
		private Boolean tourExist; // 투어 가능 여부
		private Boolean isSeismic; // 내진설계여부
		private Integer totalElevatorCount; // 총 엘리베이터 수
	}
}

package org.silsagusi.batch.scrape.naverland.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NaverLandArticleResponse {

	private String code;
	private boolean hasPaidPreSale;
	private boolean more;
	private boolean TIME;
	private Integer z;
	private Integer page;
	private List<NaverLandArticle> body;

	// 네이버 부동산 매물 정보 API 응답값 형식
	@Data
	public static class NaverLandArticle {
		private String atclNo;  // 매물 번호
		private String cortarNo;  // 법정동코드
		private String atclNm;  // 매물 이름
		private String atclStatCd;  // 매물상태코드 (R0 : 판매중?)
		private String rletTpCd;  // 매물유형코드 (A01 : 아파트, A02 : 오피스텔, G01 : 고시원)
		private String uprRletTpCd;  // 상위? 매물유형코드 (rletTpCd과 차이 없음)
		private String rletTpNm;  // 매물유형명 (아파트, 오피스텔 등)
		private String tradTpCd;  // 거래유형코드 (A1 : 매매, B1 : 전세, B2 : 월세, B3 : 단기임대)
		private String tradTpNm;  // 거래유형명 (매매, 전세 등)
		private String vrfcTpCd;  // 인증 유형 코드 (NONE : 인증되지 않은 매물, S_VR : Super? VR 확인 가능, NDOC1 : 네이버에서 올린 매물?, OWNER : 집주인이 직접 올린 매물?)
		private String flrInfo;  // 층 정보 (14/19 : 매물 층수 / 건물 층수)
		private Integer prc;  // 매매가/보증금
		private Integer rentPrc;  // 월세
		private String hanPrc;  // 한글로 표기한 매매가/보증금 (20억)
		private String spc1;  // 공급면적 (단위: 제곱미터) (68E㎡ (E: Estimated 예상값))
		private String spc2;  // 전용면적 (단위: 제곱미터) (전용42E (E: Estimated 예상값))
		private String direction;  // 방향 (남향, 북향 등)
		@JsonFormat(pattern = "yy.MM.dd.")
		private LocalDate atclCfmYmd;  // 건물 사용승인일 (25.04.16.)
		private String repImgUrl;  // 대표 이미지 URL (BASE URL : https://landthumb-phinf.pstatic.net/)
		private String repImgTpCd;  // 대표 이미지 타입 코드 (빈 값과 SITE가 붙은 값의 차이를 모르겠음)
		private String repImgThumb;  // 대표 이미지 썸네일 (f130_98) (무슨 역할인지 모르겠음..)
		private Double lat;  // 위도
		private Double lng;  // 경도
		private String atclFetrDesc;  // 매물 특징 설명 (테헤란로 밀접 삼성역과 포스코사거리 도보권 상권우수 사무실업무용 사용)
		private String cpid;  // 정보 제공 출처 코드 (NEONET : 부동산뱅크, bizmk : 매경부동산)
		private String cpNm;  // 정보 제공 출처
		private String rltrNm;  // 매물을 올린 공인중개사무소
		private Integer minMviFee;  // 최소 단기임대 비용
		private Integer maxMviFee;  // 최대 단기임대 비용
		private String sbwyInfo;  // 주변 지하철역
		private String svcCont;  // 고시원 기본 정보
		private String gdrNm;  // 고시원 특징 (남녀공용, 남녀층구분, 여성전용 등)
		private Integer etRoomCnt;  // 빈방 수
		private String tradePriceHan;  // 한글 거래가 (빈 값이어서 무슨 역할인지 모르겠음)
		private Integer tradeRentPrice;  // 보증금? (0이어서 무슨 역할인지 모르겠음)
		private Boolean tradeCheckedByOwner;  // 실매물 확인 여부
		private Boolean isVrExposed;  // VR 촬영 완료 여부
	}
}

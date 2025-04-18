package org.silsagusi.batch.scrape.naverland.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverLandMobileArticleResponse {

	private String code;
	private boolean hasPaidPreSale;
	private boolean more;
	private boolean TIME;
	private Integer z;
	private Integer page;
	private List<Body> body;

	// 네이버 부동산 매물 정보 API 응답값 형식
	@Data
	public static class Body {
		// [주석 포맷]
		// 영문 뜻, 한글 뜻
		// 예시 (참고사항)

		// article number 매물 번호
		private String atclNo;

		// Korea? area number 법정동코드
		private String cortarNo;

		// article name 매물 이름
		private String atclNm;

		// article status code 매물상태코드
		// R0 : 판매중?
		private String atclStatCd;

		// real estate type code 매물유형코드
		// A01 : 아파트
		// A02 : 오피스텔
		// G01 : 고시원
		private String rletTpCd;

		// upr? real estate type code 상위? 매물유형코드
		// rletTpCd과 차이 없음
		private String uprRletTpCd;

		// real estate type name 매물유형명 (아파트, 오피스텔 등)
		private String rletTpNm;

		// trade type code 거래유형코드
		// A1 : 매매
		// B1 : 전세
		// B2 : 월세
		// B3 : 단기임대
		private String tradTpCd;

		// trade type name 거래유형명 (매매, 전세 등)
		private String tradTpNm;

		// verification type code 인증 유형 코드
		// NONE : 인증되지 않은 매물
		// S_VR : Super? VR 확인 가능
		// NDOC1 : 네이버에서 올린 매물?
		// OWNER : 집주인이 직접 올린 매물?
		private String vrfcTpCd;

		// floor info 층 정보
		// 14/19 : 매물 층수 / 건물 층수
		private String flrInfo;

		// price 매매가/보증금
		private Integer prc;

		// rent price 월세
		private Integer rentPrc;

		// hangul price 한글로 표기한 매매가/보증금
		// 20억
		private String hanPrc;

		// 1st spec 공급면적 (단위: 제곱미터)
		// 68E㎡ (E: Estimated 예상값)
		private Double spc1;

		// 2nd spec 전용면적 (단위: 제곱미터)
		// 전용42E (E: Estimated 예상값)
		private Double spc2;

		// 방향 (남향, 북향 등)
		private String direction;

		// article confirmation date 건물 사용승인일
		// 25.04.16.
		private String atclCfmYmd;

		// representative image url 대표 이미지 URL
		// BASE URL : https://landthumb-phinf.pstatic.net/
		private String repImgUrl;

		// representative image type code
		// 빈 값과 SITE가 붙은 값의 차이를 모르겠음
		private String repImgTpCd;

		// representative image thumbnail 대표 이미지 썸네일
		// f130_98
		// 무슨 역할인지 모르겠음..
		private String repImgThumb;

		// 위도
		private Double lat;

		// 경도
		private Double lng;

		// article feature? description 매물 특징 설명
		// 테헤란로 밀접 삼성역과 포스코사거리 도보권 상권우수 사무실업무용 사용
		private String atclFetrDesc;

		// 태그 리스트
		// 0	4년이내
		// 1	역세권
		// 2	방한개
		// 3	화장실한개
		private List<String> tagList;

		// company id : 정보 제공 출처 코드
		// NEONET : 부동산뱅크
		// bizmk : 매경부동산
		private String cpid;

		// company name 정보 제공 출처명
		private String cpNm;

		// real estate trader name 매물을 올린 공인중개사 사무소명
		private String rltrNm;

		// subway info 주변 지하철명
		private String sbwyInfo;

		// trade price in hangul 한글 거래가?
		// 빈 값이어서 무슨 역할인지 모르겠음
		private String tradePriceHan;

		// trade rent price
		// 0이어서 무슨 역할인지 모르겠음
		private Integer tradeRentPrice;

		// 실매물 확인 여부
		private Boolean tradeCheckedByOwner;

		// VR 촬영 완료 여부
		private Boolean isVrExposed;
	}
}
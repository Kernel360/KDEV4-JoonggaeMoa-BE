package org.silsagusi.batch.scrape.naverland.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

// 카카오 API 응답 DTO
@Data
public class KakaoMapCoord2AddressResponse {
	private Meta meta; // 응답된 주소 개수 등의 메타 정보
	private List<Document> documents; // 도로명 주소와 지번 주소 정보를 담은 객체 리스트

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Meta {
		private int totalCount; // 반환된 주소의 총 개수
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Document {
		private RoadAddress roadAddress; // 도로명 주소 객체
		private Address address; // 지번 주소 객체
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class RoadAddress {
		@JsonProperty(value = "address_name")
		private String roadAddressName; // 전체 도로명 주소 "경기도 안성시 죽산면 죽산초교길 69-4"
		@JsonProperty(value = "region_1depth_name")
		private String sido; // 1단계 지역명 (예: 시/도) "경기"
		@JsonProperty(value = "region_2depth_name")
		private String sigungu; // 2단계 지역명 (예: 시군구) "안성시"
		@JsonProperty(value = "region_3depth_name")
		private String dongeupmyeon; // 3단계 지역명 (예: 동/읍/면) "죽산면"
		private String roadName; // 도로명 "죽산초교길"
		private String undergroundYn; // 지하 여부 (Y/N)
		private String mainBuildingNo; // 주 건물 번호 "69"
		private String subBuildingNo; // 부 건물 번호 "4"
		private String buildingName; // 건물명 "무지개아파트"
		private String zoneNo; // 우편번호 (도로명 기준) "17519"
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Address {
		@JsonProperty(value = "address_name")
		private String lotAddressName; // 전체 지번 주소 "경기 안성시 죽산면 죽산리 343-1"
		@JsonProperty(value = "region_1depth_name")
		private String city; // 1단계 지역명 (시/도) "경기"
		@JsonProperty(value = "region_2depth_name")
		private String district; // 2단계 지역명 (시군구) "안성시"
		@JsonProperty(value = "region_3depth_name")
		private String town; // 3단계 지역명 (동/읍/면) "죽산면 죽산리"
		private String mountainYn; // 산 여부 (Y/N) "N"
		private String mainAddressNo; // 주 주소 번호 ('-' 기호 앞 번지수) "343"
		private String subAddressNo; // 부 주소 번호 ('-' 기호 뒤 번지수) "1"
		private String zipCode; // 우편번호 (지번 기준, deprecated) ""
		private String roadName;
		private String mainBuildingNo;
		private String subBuildingNo;
		private String buildingName;
		private String zoneNo;
	}
}
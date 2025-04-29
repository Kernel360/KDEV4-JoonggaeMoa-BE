package org.silsagusi.batch.infrastructure.external;

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
	private String error;
	private InvalidParameters invalidParameters;

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Meta {
		private int totalCount; // 반환된 주소의 총 개수
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Document {
		@JsonProperty("road_address")
		private RoadAddress roadAddress; // 도로명 주소 객체
		private Address address; // 지번 주소 객체
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class RoadAddress {
		@JsonProperty("address_name")
		private String addressName; // 전체 도로명 주소 "경기도 안성시 죽산면 죽산초교길 69-4"
		@JsonProperty("region_1depth_name")
		private String region1DepthName; // 1단계 지역명 (예: 시/도) "경기"
		@JsonProperty("region_2depth_name")
		private String region2DepthName; // 2단계 지역명 (예: 시군구) "안성시"
		@JsonProperty("region_3depth_name")
		private String region3DepthName; // 3단계 지역명 (예: 동/읍/면) "죽산면"
		@JsonProperty("road_name")
		private String roadName; // 도로명 "죽산초교길"
		@JsonProperty("underground_yn")
		private String undergroundYn; // 지하 여부 (Y/N)
		@JsonProperty("main_building_no")
		private String mainBuildingNo; // 주 건물 번호 "69"
		@JsonProperty("sub_building_no")
		private String subBuildingNo; // 부 건물 번호 "4"
		@JsonProperty("building_name")
		private String buildingName; // 건물명 "무지개아파트"
		@JsonProperty("zone_no")
		private String zoneNo; // 우편번호 (도로명 기준) "17519"
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Address {
		@JsonProperty("address_name")
		private String addressName; // 전체 지번 주소 "경기 안성시 죽산면 죽산리 343-1"
		@JsonProperty("region_1depth_name")
		private String region1DepthName; // 1단계 지역명 (시/도) "경기"
		@JsonProperty("region_2depth_name")
		private String region2DepthName; // 2단계 지역명 (시군구) "안성시"
		@JsonProperty("region_3depth_name")
		private String region3DepthName; // 3단계 지역명 (동/읍/면) "죽산면 죽산리"
		@JsonProperty("mountain_yn")
		private String mountainYn; // 산 여부 (Y/N) "N"
		@JsonProperty("main_address_no")
		private String mainAddressNo; // 주 주소 번호 ('-' 기호 앞 번지수) "343"
		@JsonProperty("sub_address_no")
		private String subAddressNo; // 부 주소 번호 ('-' 기호 뒤 번지수) "1"
		@JsonProperty("zip_code")
		private String zipCode; // 우편번호 (지번 기준, deprecated) ""
	}

	@Data
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class InvalidParameters {
		private String x;
	}

	public static AddressResponse toResponse(KakaoMapCoord2AddressResponse response) {
	    if (response == null
	        || response.getDocuments() == null
	        || response.getDocuments().isEmpty()) {
	        return new AddressResponse(
	            null, null,
	            null, null, null, null, null,
	            null, null, null, null, null
	        );
	    }
	    KakaoMapCoord2AddressResponse.Document doc = response.getDocuments().get(0);
	    KakaoMapCoord2AddressResponse.Address addr = doc.getAddress();
	    KakaoMapCoord2AddressResponse.RoadAddress road = doc.getRoadAddress();
	    return new AddressResponse(
	        addr.getAddressName(), road != null ? road.getAddressName() : null,
	        addr.getRegion1DepthName(), addr.getRegion2DepthName(), addr.getRegion3DepthName(),
	        addr.getMainAddressNo(), addr.getSubAddressNo(),
	        road != null ? road.getRoadName() : null,
	        road != null ? road.getMainBuildingNo() : null,
	        road != null ? road.getSubBuildingNo() : null,
	        road != null ? road.getBuildingName() : null,
	        road != null ? road.getZoneNo() : null
	    );
	}
}
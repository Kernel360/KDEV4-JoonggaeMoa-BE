package org.silsagusi.joonggaemoa.request.naverland.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// 카카오 API 응답 DTO
public class Coord2AddressResponse {

    // meta: 응답된 주소 개수 등의 메타 정보
    private Meta meta;

    // documents: 도로명 주소와 지번 주소 정보를 담은 객체 리스트
    private List<Document> documents;

    @Data
    public static class Meta {

        // total_count: 반환된 주소의 총 개수
        private int total_count;

    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Document {

        // road_address: 도로명 주소 객체
        private RoadAddress roadAddress;

        // address: 지번 주소 객체
        private Address address;

    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoadAddress {

        // address_name: 전체 도로명 주소
        // "경기도 안성시 죽산면 죽산초교길 69-4"
        private String addressName;

        // region_1depth_name: 1단계 지역명 (예: 시/도)
        // "경기"
        private String region1DepthName;

        // region_2depth_name: 2단계 지역명 (예: 시군구)
        // "안성시"
        private String region2DepthName;

        // region_3depth_name: 3단계 지역명 (예: 동/읍/면)
        // "죽산면"
        private String region3DepthName;

        // road_name: 도로명
        // "죽산초교길"
        private String roadName;

        // underground_yn: 지하 여부 (Y/N)
        // "N"
        private String undergroundYn;

        // main_building_no: 주 건물 번호
        // "69"
        private String mainBuildingNo;

        // sub_building_no: 부 건물 번호
        // "4"
        private String subBuildingNo;

        // building_name: 건물명
        // "무지개아파트"
        private String buildingName;

        // zone_no: 우편번호 (도로명 기준)
        // "17519"
        private String zoneNo;

    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Address {

        // address_name: 전체 지번 주소
        // "경기 안성시 죽산면 죽산리 343-1"
        private String addressName;

        // region_1depth_name: 1단계 지역명 (예: 시/도)
        // "경기"
        private String region1DepthName;

        // region_2depth_name: 2단계 지역명 (예: 시군구)
        // "안성시"
        private String region2DepthName;

        // region_3depth_name: 3단계 지역명 (예: 동/읍/면)
        // "죽산면 죽산리"
        private String region3DepthName;

        // mountain_yn: 산 여부 (Y/N)
        // "N"
        private String mountainYn;

        // main_address_no: 주 주소 번호 ('-' 기호 앞 번지수)
        // "343"
        private String mainAddressNo;

        // sub_address_no: 부 주소 번호 ('-' 기호 뒤 번지수)
        // "1"
        private String subAddressNo;

    }
}
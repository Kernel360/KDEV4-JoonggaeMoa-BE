package org.silsagusi.joonggaemoa.request.kakao.service;

import lombok.Data;
import java.util.List;

@Data
// 카카오 API 응답 DTO
public class AddressResponse {

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
    public static class Document {

        // road_address: 도로명 주소 객체
        private RoadAddress road_address;

        // address: 지번 주소 객체
        private Address address;

    }

    @Data
    public static class RoadAddress {

        // address_name: 전체 도로명 주소
        private String address_name;

        // region_1depth_name: 1단계 지역명 (예: 시/도)
        private String region_1depth_name;

        // region_2depth_name: 2단계 지역명 (예: 시군구)
        private String region_2depth_name;

        // region_3depth_name: 3단계 지역명 (예: 동/읍/면)
        private String region_3depth_name;

        // road_name: 도로명
        private String road_name;

        // underground_yn: 지하 여부 (Y/N)
        private String underground_yn;

        // main_building_no: 주 건물 번호
        private String main_building_no;

        // sub_building_no: 부 건물 번호
        private String sub_building_no;

        // building_name: 건물명
        private String building_name;

        // zone_no: 우편번호 (도로명 기준)
        private String zone_no;

    }

    @Data
    public static class Address {

        // address_name: 전체 지번 주소
        private String address_name;

        // region_1depth_name: 1단계 지역명 (예: 시/도)
        private String region_1depth_name;

        // region_2depth_name: 2단계 지역명 (예: 시군구)
        private String region_2depth_name;

        // region_3depth_name: 3단계 지역명 (예: 동/읍/면)
        private String region_3depth_name;

        // mountain_yn: 산 여부 (Y/N)
        private String mountain_yn;

        // main_address_no: 주 주소 번호
        private String main_address_no;

        // sub_address_no: 부 주소 번호
        private String sub_address_no;

        // zip_code: 우편번호 (지번 기준)
        private String zip_code;

    }
}
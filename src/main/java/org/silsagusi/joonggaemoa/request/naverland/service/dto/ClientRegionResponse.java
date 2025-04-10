package org.silsagusi.joonggaemoa.request.naverland.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class ClientRegionResponse {

	private List<Region> regionList;

	@Data
	public static class Region {

		// 법정동 코드
		private String cortarNo;

		// 화면 정가운데 위도
		private Double centerLat;

		// 화면 정가운데 경도
		private Double centerLon;

		// 법정동명
		private String cortarName;

		/* 법정동 타입
		 * city : 시/도
		 * dvsn (division) : 시/군/구
		 * sec (section) : 읍/면/동
		 */
		private String cortarType;
	}
}

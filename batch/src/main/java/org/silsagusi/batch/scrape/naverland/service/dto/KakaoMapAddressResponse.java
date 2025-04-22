package org.silsagusi.batch.scrape.naverland.service.dto;

import lombok.Getter;

@Getter
public class KakaoMapAddressResponse {
	private final String lotAddress;
	private final String roadAddress;
	private final String city;
	private final String district;
	private final String region;
	private final String mainAddressNo;
	private final String subAddressNo;
	private final String roadName;
	private final String mainBuildingNo;
	private final String subBuildingNo;
	private final String buildingName;
	private final String zoneNo;

	public KakaoMapAddressResponse(
		String lotAddress,
		String roadAddress,
		String city,
		String district,
		String region,
		String mainAddressNo,
		String subAddressNo,
		String roadName,
		String mainBuildingNo,
		String subBuildingNo,
		String buildingName,
		String zoneNo
	) {
		this.lotAddress = lotAddress;
		this.roadAddress = roadAddress;
		this.city = city;
		this.district = district;
		this.region = region;
		this.mainAddressNo = mainAddressNo;
		this.subAddressNo = subAddressNo;
		this.roadName = roadName;
		this.mainBuildingNo = mainBuildingNo;
		this.subBuildingNo = subBuildingNo;
		this.buildingName = buildingName;
		this.zoneNo = zoneNo;
	}
}
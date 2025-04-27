package org.silsagusi.batch.infrastructure.external;

import lombok.Getter;

@Getter
public class AddressResponse {
	private String lotAddress;
	private String roadAddress;
	private String city;
	private String district;
	private String region;
	private String mainAddressNo;
	private String subAddressNo;
	private String roadName;
	private String mainBuildingNo;
	private String subBuildingNo;
	private String buildingName;
	private String zoneNo;

	public AddressResponse(
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

	public static AddressResponse toResponse(
		KakaoMapCoord2AddressResponse response
	) {
		if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
			return null;
		}

		KakaoMapCoord2AddressResponse.Document doc = response.getDocuments().get(0);

		if (doc.getAddress() == null) {
			return null;
		} else {
			String lotAddress = doc.getAddress().getLotAddressName();
			String city = doc.getAddress().getCity();
			String district = doc.getAddress().getDistrict();
			String town = doc.getAddress().getTown();
			String mainAddressNo = doc.getAddress().getMainAddressNo();
			String subAddressNo = doc.getAddress().getSubAddressNo();

			if (doc.getRoadAddress() == null) {
				return new AddressResponse(
					lotAddress, null,
					city, district, town, mainAddressNo, subAddressNo,
					null, null, null, null, null);
			} else {
				String roadAddress = doc.getRoadAddress().getRoadAddressName();
				String roadName = doc.getRoadAddress().getRoadName();
				String mainBuildingNo = doc.getRoadAddress().getMainBuildingNo();
				String subBuildingNo = doc.getRoadAddress().getSubBuildingNo();
				String buildingName = doc.getRoadAddress().getBuildingName();
				String zoneNo = doc.getRoadAddress().getZoneNo();

				return new AddressResponse(
					lotAddress, roadAddress,
					city, district, town, mainAddressNo, subAddressNo,
					roadName, mainBuildingNo, subBuildingNo, buildingName, zoneNo);
			}
		}
	}
}
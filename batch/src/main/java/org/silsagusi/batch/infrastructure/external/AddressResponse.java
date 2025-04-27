package org.silsagusi.batch.infrastructure.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
}
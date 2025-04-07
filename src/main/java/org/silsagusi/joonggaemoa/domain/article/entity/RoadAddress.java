package org.silsagusi.joonggaemoa.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.request.kakao.service.dto.AddressResponse;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "road_addresses")
@Getter
public class RoadAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column(name = "road_address")
	private String addressName;

	@Column(name = "city")
	private String region1DepthName;

	@Column(name = "district")
	private String region2DepthName;

	@Column(name = "town")
	private String region3DepthName;

	private String roadName;

	@Column(name = "is_underground")
	private Boolean undergroundYn;

	private String mainBuildingNo;

	private String subBuildingNo;

	private String buildingName;

	@Column(name = "zipcode")
	private String zoneNo;

	public RoadAddress(Double latitude, Double longitude, String addressName,
	                   String region1DepthName, String region2DepthName, String region3DepthName,
	                   String roadName, Boolean undergroundYn, String mainBuildingNo,
	                   String subBuildingNo, String buildingName, String zoneNo
	                   ) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.addressName = addressName;
		this.region1DepthName = region1DepthName;
		this.region2DepthName = region2DepthName;
		this.region3DepthName = region3DepthName;
		this.roadName = roadName;
		this.undergroundYn = undergroundYn;
		this.mainBuildingNo = mainBuildingNo;
		this.subBuildingNo = subBuildingNo;
		this.buildingName = buildingName;
		this.zoneNo = zoneNo;
	}

	public static RoadAddress createFrom(Double lat, Double lon, AddressResponse.RoadAddress addr) {
		return new RoadAddress(
			lat,
			lon,
			addr.getAddress_name(),
			addr.getRegion_1depth_name(),
			addr.getRegion_2depth_name(),
			addr.getRegion_3depth_name(),
			addr.getRoad_name(),
			addr.getUnderground_yn().equals("Y"),
			addr.getMain_building_no(),
			addr.getSub_building_no(),
			addr.getBuilding_name(),
			addr.getZone_no()
		);
	}
}

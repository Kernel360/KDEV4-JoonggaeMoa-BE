package org.silsagusi.joonggaemoa.domain.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.request.kakao.service.dto.AddressResponse;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "lot_addresses")
@Getter
public class LotAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false)
	private Long id;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column(name = "lot_address")
	private String addressName;

	@Column(name = "city")
	private String region1DepthName;

	@Column(name = "district")
	private String region2DepthName;

	@Column(name = "town")
	private String region3DepthName;

	@Column(name = "is_mountain")
	private Boolean mountainYn;

	private String mainAddressNo;

	private String subAddressNo;

	@Column(name = "zipcode_old")
	private String zipCode;

	public LotAddress(Double latitude, Double longitude, String addressName,
	                  String region1DepthName, String region2DepthName, String region3DepthName,
	                  Boolean mountainYn, String mainAddressNo, String subAddressNo, String zipCode) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.addressName = addressName;
		this.region1DepthName = region1DepthName;
		this.region2DepthName = region2DepthName;
		this.region3DepthName = region3DepthName;
		this.mountainYn = mountainYn;
		this.mainAddressNo = mainAddressNo;
		this.subAddressNo = subAddressNo;
		this.zipCode = zipCode;
	}

	public static LotAddress createFrom(Double lat, Double lon, AddressResponse.Address addr) {
		return new LotAddress(
			lat,
			lon,
			addr.getAddress_name(),
			addr.getRegion_1depth_name(),
			addr.getRegion_2depth_name(),
			addr.getRegion_3depth_name(),
			addr.getMountain_yn().equals("Y"),
			addr.getMain_address_no(),
			addr.getSub_address_no(),
			addr.getZip_code()
		);
	}
}

package org.silsagusi.joonggaemoa.global.address.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.Coord2AddressResponse;

@Getter
@AllArgsConstructor
public class AddressDto {
	private String lotAddress;
	private String roadAddress;

	public Coord2AddressResponse.Address toLotAddress() {
		Coord2AddressResponse.Address addr = new Coord2AddressResponse.Address();
		addr.setAddressName(this.lotAddress);
		return addr;
	}

	public Coord2AddressResponse.RoadAddress toRoadAddress() {
		if (this.roadAddress == null) return null;

		Coord2AddressResponse.RoadAddress roadAddr = new Coord2AddressResponse.RoadAddress();
		roadAddr.setAddressName(this.roadAddress);
		return roadAddr;
	}
}
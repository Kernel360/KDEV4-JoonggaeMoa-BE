package org.silsagusi.joonggaemoa.global.address;

import org.silsagusi.joonggaemoa.global.address.dto.AddressDto;

public abstract class AbstractAddressLookupService {

	public abstract AddressDto lookupAddress(double latitude, double longitude);

}
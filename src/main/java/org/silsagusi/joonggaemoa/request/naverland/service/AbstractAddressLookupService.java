package org.silsagusi.joonggaemoa.request.naverland.service;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.AddressResponse;

public abstract class AbstractAddressLookupService {

	public abstract AddressResponse lookupAddress(double latitude, double longitude);

}
package org.silsagusi.joonggaemoa.request.naverland.service;

import org.silsagusi.joonggaemoa.request.naverland.service.dto.AddressResponse;

public interface AbstractAddressLookupService {

	AddressResponse lookupAddress(double latitude, double longitude);

}
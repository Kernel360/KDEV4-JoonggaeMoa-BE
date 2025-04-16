package org.silsagusi.joonggaemoa.batch.naverland.service;

import org.silsagusi.joonggaemoa.batch.naverland.service.dto.AddressResponse;

public interface AbstractAddressLookupService {

	AddressResponse lookupAddress(double latitude, double longitude);

}
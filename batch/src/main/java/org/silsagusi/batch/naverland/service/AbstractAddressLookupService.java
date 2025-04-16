package org.silsagusi.batch.naverland.service;

import org.silsagusi.batch.naverland.service.dto.AddressResponse;

public interface AbstractAddressLookupService {

	AddressResponse lookupAddress(double latitude, double longitude);

}
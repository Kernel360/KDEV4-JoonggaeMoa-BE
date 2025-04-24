package org.silsagusi.batch.scrape.naverland.service;

import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;

public interface KakaoMapAddressLookupInterface {
	KakaoMapAddressResponse lookupAddress(double latitude, double longitude);
}
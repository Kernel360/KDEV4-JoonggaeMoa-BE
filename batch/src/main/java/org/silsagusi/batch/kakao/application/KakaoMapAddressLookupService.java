// package org.silsagusi.batch.kakao.application;
//
// import org.silsagusi.batch.kakao.infrastructure.KakaoMapAddressResponse;
// import org.silsagusi.batch.infrastructure.external.KakaoMapApiClient;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Service
// @RequiredArgsConstructor
// @Slf4j
// public class KakaoMapAddressLookupService implements KakaoMapAddressLookupInterface {
//
// 	private final KakaoMapApiClient kakaoMapApiClient;
//
// 	@Override
// 	@Cacheable(value = "addressCache", key = "#latitude + ':' + #longitude")
// 	public KakaoMapAddressResponse lookupAddress(double latitude, double longitude) throws NullPointerException {
// 		// KakaoMapCoord2AddressResponse response = kakaoMapApiClient.getAddr(longitude, latitude);
//
// 	}
//
// }

package org.silsagusi.batch.naverland.service;

import org.silsagusi.batch.naverland.client.KakaoApiClient;
import org.silsagusi.batch.naverland.service.dto.AddressResponse;
import org.silsagusi.batch.naverland.service.dto.Coord2AddressResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAddressLookupService implements AbstractAddressLookupService {

	private final KakaoApiClient kakaoApiClient;

	@Override
	@Cacheable(value = "addressCache", key = "#latitude + ':' + #longitude")
	public AddressResponse lookupAddress(double latitude, double longitude)
		throws NullPointerException {

		// Call Kakao API client to fetch address JSON
		Coord2AddressResponse response = kakaoApiClient.getAddr(longitude, latitude);

		if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
			log.warn("No address found for lat={}, lon={}", latitude, longitude);
			return null;  // or throw if no address found
		}

		Coord2AddressResponse.Document doc = response.getDocuments().get(0);

		if (doc.getAddress() == null) {
			log.warn("주소 정보 없음: lat={}, lon={}", latitude, longitude);
			return null;  // Return null or handle as needed
		} else {
			String lotAddress = doc.getAddress().getLotAddressName();
			String city = doc.getAddress().getCity();
			String district = doc.getAddress().getDistrict();
			String town = doc.getAddress().getTown();
			String mainAddressNo = doc.getAddress().getMainAddressNo();
			String subAddressNo = doc.getAddress().getSubAddressNo();

			if (doc.getRoadAddress() == null) {
				log.warn("도로명 주소 정보 없음: lat={}, lon={}", latitude, longitude);
				return new AddressResponse(lotAddress, null,
					city, district, town, mainAddressNo, subAddressNo,
					null, null, null, null, null);
			} else {
				String roadAddress = doc.getRoadAddress().getRoadAddressName();
				String roadName = doc.getRoadAddress().getRoadName();
				String mainBuildingNo = doc.getRoadAddress().getMainBuildingNo();
				String subBuildingNo = doc.getRoadAddress().getSubBuildingNo();
				String buildingName = doc.getRoadAddress().getBuildingName();
				String zoneNo = doc.getRoadAddress().getZoneNo();

				return new AddressResponse(lotAddress, roadAddress,
					city, district, town, mainAddressNo, subAddressNo,
					roadName, mainBuildingNo, subBuildingNo, buildingName, zoneNo);
			}
		}
	}

}

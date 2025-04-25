package org.silsagusi.batch.scrape.naverland.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.batch.scrape.naverland.client.KakaoMapApiClient;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapAddressResponse;
import org.silsagusi.batch.scrape.naverland.service.dto.KakaoMapCoord2AddressResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoMapAddressLookupService implements KakaoMapAddressLookupInterface {

	private final KakaoMapApiClient kakaoMapApiClient;

	@Override
	@Cacheable(value = "addressCache", key = "#latitude + ':' + #longitude")
	public KakaoMapAddressResponse lookupAddress(double latitude, double longitude) throws NullPointerException {
		KakaoMapCoord2AddressResponse response = kakaoMapApiClient.getAddr(longitude, latitude);

		if (response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
			return null;
		}
		KakaoMapCoord2AddressResponse.Document doc = response.getDocuments().get(0);

		if (doc.getAddress() == null) {
			return null;
		} else {
			String lotAddress = doc.getAddress().getLotAddressName();
			String city = doc.getAddress().getCity();
			String district = doc.getAddress().getDistrict();
			String town = doc.getAddress().getTown();
			String mainAddressNo = doc.getAddress().getMainAddressNo();
			String subAddressNo = doc.getAddress().getSubAddressNo();

			if (doc.getRoadAddress() == null) {
				return new KakaoMapAddressResponse(
					lotAddress, null,
					city, district, town, mainAddressNo, subAddressNo,
					null, null, null, null, null);
			} else {
				String roadAddress = doc.getRoadAddress().getRoadAddressName();
				String roadName = doc.getRoadAddress().getRoadName();
				String mainBuildingNo = doc.getRoadAddress().getMainBuildingNo();
				String subBuildingNo = doc.getRoadAddress().getSubBuildingNo();
				String buildingName = doc.getRoadAddress().getBuildingName();
				String zoneNo = doc.getRoadAddress().getZoneNo();

				return new KakaoMapAddressResponse(
					lotAddress, roadAddress,
					city, district, town, mainAddressNo, subAddressNo,
					roadName, mainBuildingNo, subBuildingNo, buildingName, zoneNo);
			}
		}
	}

}

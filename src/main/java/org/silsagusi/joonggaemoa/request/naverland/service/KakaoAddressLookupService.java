package org.silsagusi.joonggaemoa.request.naverland.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.global.address.AbstractAddressLookupService;
import org.silsagusi.joonggaemoa.global.address.dto.AddressDto;
import org.silsagusi.joonggaemoa.request.naverland.client.KakaoApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.Coord2AddressResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoAddressLookupService extends AbstractAddressLookupService {

	private final KakaoApiClient kakaoApiClient;

	@Override
	public AddressDto lookupAddress(double latitude, double longitude) {

		// Call Kakao API client to fetch address JSON
		Coord2AddressResponse response = kakaoApiClient.getAddr(latitude, longitude);

		if (response.getDocuments() == null || response.getDocuments().isEmpty()) {
			return null;  // or throw if no address found
		}

		Coord2AddressResponse.Document doc = response.getDocuments().get(0);
		String lotAddress = doc.getAddress().getAddressName();

		// Extract road address if available
		String roadAddress = null;
		if (doc.getRoadAddress() != null) {
			roadAddress = doc.getRoadAddress().getAddressName();
		}

		// Create DTO with the addresses
		return new AddressDto(lotAddress, roadAddress);
	}

}

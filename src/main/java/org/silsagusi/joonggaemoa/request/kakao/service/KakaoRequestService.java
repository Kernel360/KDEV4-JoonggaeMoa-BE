package org.silsagusi.joonggaemoa.request.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.article.entity.LotAddress;
import org.silsagusi.joonggaemoa.domain.article.entity.RoadAddress;
import org.silsagusi.joonggaemoa.domain.article.repository.LotAddressRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.RoadAddressRepository;
import org.silsagusi.joonggaemoa.request.kakao.client.KakaoApiClient;
import org.silsagusi.joonggaemoa.request.kakao.service.dto.AddressResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoRequestService {

	private final KakaoApiClient kakaoApiClient;
	private final RoadAddressRepository roadAddressRepository;
	private final LotAddressRepository lotAddressRepository;

	public void getAddress(Double lat, Double lon) throws InterruptedException {
		log.info("카카오 부동산 매물 정보 크롤링 시작");

		AddressResponse response = kakaoApiClient.getAddr(lat, lon);

		if (response.getDocuments() != null && !response.getDocuments().isEmpty()) {
			AddressResponse.Document doc = response.getDocuments().get(0);

			if (doc.getRoad_address() == null) {

				// 도로명 주소가 없으므로 지번 주소 사용 또는 별도 로직 처리
				AddressResponse.Address lotAddr = doc.getAddress();

				// lotAddr 활용
				List<LotAddress> lotAddresses = mapToLotAddresses(lat, lon, (List<AddressResponse.Address>) lotAddr);
				lotAddressRepository.saveAll(lotAddresses);

				// 2~7초 랜덤 딜레이
				Thread.sleep((long)(Math.random() * 5000 + 2000));

			} else {

				// 정상적으로 도로명 주소 사용
				AddressResponse.RoadAddress roadAddr = doc.getRoad_address();

				// roadAddr 활용
				List<RoadAddress> roadAddresses = mapToRoadAddresses(lat, lon, (List<AddressResponse.RoadAddress>) roadAddr);
				roadAddressRepository.saveAll(roadAddresses);
			}

		}
	}

	private List<RoadAddress> mapToRoadAddresses(Double lat, Double lon, List<AddressResponse.RoadAddress> addresses) {
		return addresses.stream()
			.map(addr -> RoadAddress.createFrom(lat, lon, addr))
			.toList();
	}

	private List<LotAddress> mapToLotAddresses(Double lat, Double lon, List<AddressResponse.Address> addresses) {
		return addresses.stream()
			.map(addr -> LotAddress.createFrom(lat, lon, addr))
			.toList();
	}
}

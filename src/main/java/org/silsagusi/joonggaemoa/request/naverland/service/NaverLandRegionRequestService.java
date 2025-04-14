package org.silsagusi.joonggaemoa.request.naverland.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionScrapStatusRepository;
import org.silsagusi.joonggaemoa.domain.article.service.dto.RegionResponse;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientRegionResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NaverLandRegionRequestService {

	private final NaverLandApiClient naverLandApiClient;
	private final RegionRepository regionRepository;
	private final RegionScrapStatusRepository regionScrapStatusRepository;

	@Transactional
	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		ClientRegionResponse rootResponse = naverLandApiClient.fetchRegionList("0000000000");
		if (rootResponse != null && rootResponse.getRegionList() != null) {
			updateRegionsFromResponse(rootResponse);
			List<String> sidoCodes = extractCortarNos(rootResponse);
			processSidoRegions(sidoCodes);
		} else {
			log.error("루트 수준 지역을 가져오지 못했습니다");
		}

		log.info("지역 초기화가 완료되었습니다. 총 지역 수: {}", regionRepository.count());
	}

	private List<String> extractCortarNos(ClientRegionResponse response) {
		List<String> cortarNos = new ArrayList<>();
		response.getRegionList().forEach(region -> cortarNos.add(region.getCortarNo()));
		return cortarNos;
	}

	private void processSidoRegions(List<String> sidoCodes) {
		for (String sidoCode : sidoCodes) {
			ClientRegionResponse sigunguResponse = naverLandApiClient.fetchRegionList(sidoCode);
			if (sigunguResponse != null && sigunguResponse.getRegionList() != null) {
				updateRegionsFromResponse(sigunguResponse);
				List<String> sigunguCodes = extractCortarNos(sigunguResponse);
				processSigunguRegions(sigunguCodes);
			} else {
				log.warn("시도에 대한 시군구 수준 지역이 없습니다: {}", sidoCode);
			}
		}
	}

	private void processSigunguRegions(List<String> sigunguCodes) {
		for (String sigunguCode : sigunguCodes) {
			ClientRegionResponse dongResponse = naverLandApiClient.fetchRegionList(sigunguCode);
			if (dongResponse != null && dongResponse.getRegionList() != null) {
				updateRegionsFromResponse(dongResponse);
			} else {
				log.warn("시군구에 대한 동 수준 지역이 없습니다: {}", sigunguCode);
			}
		}
	}

	private void updateRegionsFromResponse(ClientRegionResponse response) {
		response.getRegionList().forEach(apiRegion -> {
			// Build RegionResponse DTO from API data
			RegionResponse regionResponse = RegionResponse.builder()
				.cortarNo(apiRegion.getCortarNo())
				.centerLat(apiRegion.getCenterLat())
				.centerLon(apiRegion.getCenterLon())
				.cortarName(apiRegion.getCortarName())
				.cortarType(apiRegion.getCortarType())
				.build();

			// Find existing region and update it using builder
			List<Region> existingRegions = regionRepository.findAllByCortarNoIn(List.of(regionResponse.getCortarNo()));
			Region existingRegion = existingRegions.isEmpty() ? null : existingRegions.get(0);

			Region updatedRegion;
			if (existingRegion != null) {
				existingRegion.updateRegion(
					regionResponse.getCenterLat(),
					regionResponse.getCenterLon(),
					regionResponse.getCortarName(),
					regionResponse.getCortarType()
				);
				updatedRegion = existingRegion;
			} else {
				updatedRegion = Region.builder()
					.cortarNo(regionResponse.getCortarNo())
					.centerLat(regionResponse.getCenterLat())
					.centerLon(regionResponse.getCenterLon())
					.cortarName(regionResponse.getCortarName())
					.cortarType(regionResponse.getCortarType())
					.build();
			}
			regionRepository.save(updatedRegion);

			List<RegionScrapStatus> statusList = regionScrapStatusRepository.findByRegion_CortarNo(updatedRegion.getCortarNo());
			if (!statusList.isEmpty()) {
				RegionScrapStatus existingStatus = statusList.get(0);
				existingStatus.updatePage(1, LocalDateTime.now());
				existingStatus.updateCompleted(false);
				existingStatus.updateFailed(false, null);
				regionScrapStatusRepository.save(existingStatus);
			} else {
				RegionScrapStatus newStatus = new RegionScrapStatus(updatedRegion, 1, false, LocalDateTime.now());
				regionScrapStatusRepository.save(newStatus);
			}
		});
	}
}

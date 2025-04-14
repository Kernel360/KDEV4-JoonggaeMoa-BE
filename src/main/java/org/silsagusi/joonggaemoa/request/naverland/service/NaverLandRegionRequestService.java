package org.silsagusi.joonggaemoa.request.naverland.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.silsagusi.joonggaemoa.domain.article.entity.Region;
import org.silsagusi.joonggaemoa.domain.article.entity.RegionScrapStatus;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionRepository;
import org.silsagusi.joonggaemoa.domain.article.repository.RegionScrapStatusRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.NaverLandApiClient;
import org.silsagusi.joonggaemoa.request.naverland.service.dto.ClientRegionResponse;
import org.silsagusi.joonggaemoa.domain.article.service.dto.RegionResponse;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		log.info("Starting region initialization...");
		
		// Step 1: Start with the national level (all zeros) to get city/province level (시도)
		ClientRegionResponse rootResponse = naverLandApiClient.fetchRegionList("0000000000");
		if (rootResponse != null && rootResponse.getRegionList() != null) {
			updateRegionsFromResponse(rootResponse);
			
			// Step 2: For each city/province, get district level (시군구)
			List<String> sidoCodes = new ArrayList<>();
			rootResponse.getRegionList().forEach(sido -> {
				sidoCodes.add(sido.getCortarNo());
			});
			
			for (String sidoCode : sidoCodes) {
				ClientRegionResponse sigunguResponse = naverLandApiClient.fetchRegionList(sidoCode);
				if (sigunguResponse != null && sigunguResponse.getRegionList() != null) {
					updateRegionsFromResponse(sigunguResponse);
					
					// Step 3: For each district, get town/township/neighborhood level (읍면동)
					List<String> sigunguCodes = new ArrayList<>();
					sigunguResponse.getRegionList().forEach(sigungu -> {
						sigunguCodes.add(sigungu.getCortarNo());
					});
					
					for (String sigunguCode : sigunguCodes) {
						ClientRegionResponse dongResponse = naverLandApiClient.fetchRegionList(sigunguCode);
						if (dongResponse != null && dongResponse.getRegionList() != null) {
							updateRegionsFromResponse(dongResponse);
						} else {
							log.warn("No dong-level regions found for sigungu: {}", sigunguCode);
						}
					}
				} else {
					log.warn("No sigungu-level regions found for sido: {}", sidoCode);
				}
			}
		} else {
			log.error("Failed to fetch root-level regions");
		}
		
		log.info("Region initialization completed. Total regions: {}", regionRepository.count());
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
			Region existingRegion = regionRepository.findRegionEntityByCortarNo(regionResponse.getCortarNo())
				.orElse(null);
	
			Region updatedRegion;
			if (existingRegion != null) {
				updatedRegion = Region.updateBuilder()
					.update(existingRegion)
					.centerLat(regionResponse.getCenterLat())
					.centerLon(regionResponse.getCenterLon())
					.cortarName(regionResponse.getCortarName())
					.cortarType(regionResponse.getCortarType())
					.build();
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

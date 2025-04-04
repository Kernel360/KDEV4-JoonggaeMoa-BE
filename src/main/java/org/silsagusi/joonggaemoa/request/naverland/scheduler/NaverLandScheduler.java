package org.silsagusi.joonggaemoa.request.naverland.scheduler;

import org.silsagusi.joonggaemoa.request.naverland.service.NaverLandRequestService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NaverLandScheduler {

	private final NaverLandRequestService naverLandRequestService;

	// 기본값: 30분마다 실행
	@Scheduled(initialDelay = 0, fixedDelay = 1800000)
	public void crawlNaverLand() throws InterruptedException {
		naverLandRequestService.scrap();
	}
}

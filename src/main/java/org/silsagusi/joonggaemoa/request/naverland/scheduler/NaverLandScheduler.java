package org.silsagusi.joonggaemoa.request.naverland.scheduler;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.request.naverland.service.NaverLandRequestService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaverLandScheduler {

    private final NaverLandRequestService naverLandRequestService;

    // 기본값: 30분마다 실행
    @Scheduled(cron = "0 */40 * * * *")
    public void crawlNaverLand() throws InterruptedException {
        naverLandRequestService.scrap();
    }
}

package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.scheduler;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.NaverLandCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NaverLandScheduler {
    @Autowired
    private NaverLandCrawlerService naverLandCrawlerService;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 * * * *")
    public void crawlNaverLand() {
        naverLandCrawlerService.startCrawling();
    }
}

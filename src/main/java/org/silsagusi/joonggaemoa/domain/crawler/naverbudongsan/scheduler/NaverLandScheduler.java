package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.scheduler;

import jakarta.annotation.PostConstruct;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.NaverLandCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NaverLandScheduler {

    @Autowired
    private NaverLandCrawlerService naverLandCrawlerService;

    @PostConstruct
    public void initCrawl() {
        naverLandCrawlerService.startCrawling(); // 앱 초기 실행 시 한 번 실행
    }

    // 기본값: 30분마다 실행
    @Scheduled(cron = "0 */30 * * * *")
    public void crawlNaverLand() {
        naverLandCrawlerService.startCrawling();
    }
}

package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.scheduler;

import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NaverLandScheduleTask {
    @Autowired
    private CrawlerService crawlerService;

    @Scheduled(cron = "0 0 * * * *")
    public void crawlNaverLand() {
        crawlerService.startCrawling();
    }
}

package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.CrawlerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/naver-budongsan/properties")
@RequiredArgsConstructor
public class NaverBudongsanPropertyController {

    private final CrawlerService crawlerService;

    // TODO: API 만들기

}

package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Complex;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.NaverLandCrawlerService;
import org.springframework.web.bind.annotation.*;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;

import java.util.Optional;

@RestController
@RequestMapping("/api/naver-budongsan/properties")
@RequiredArgsConstructor
public class NaverBudongsanPropertyController {

    private final NaverLandCrawlerService naverLandCrawlerService;

    @PostMapping("/{atclNo}/articles")
    public Optional<Article> postArticle(@PathVariable("atclNo") String atclNo) {
        return null;
    }

    @PostMapping("/{atclNo}/complexes")
    public Optional<Complex> postComplex(@PathVariable("atclNo") String atclNo) {
        return null;
    }

//    @GetMapping(value = "/{atclNo}/articles")
//    public
}

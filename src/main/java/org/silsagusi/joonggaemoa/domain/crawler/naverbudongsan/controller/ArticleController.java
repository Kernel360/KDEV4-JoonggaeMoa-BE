package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.controller.dto.ArticleResponse;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.NaverLandCrawlerService;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.command.ArticleCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/naverbudongsan")
@RequiredArgsConstructor
public class ArticleController {

    private final NaverLandCrawlerService service;

    @GetMapping("/articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getArticles() {
        List<ArticleCommand> articleCommandList = service.getAllArticles();
        List<ArticleResponse> articleResponseList = articleCommandList.stream()
                .map(ArticleResponse::of).toList();

        return ResponseEntity.ok(ApiResponse.ok(articleResponseList));
    }
}

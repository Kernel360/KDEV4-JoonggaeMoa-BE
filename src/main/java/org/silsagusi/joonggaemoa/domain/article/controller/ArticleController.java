package org.silsagusi.joonggaemoa.domain.article.controller;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.article.service.ArticleResponse;
import org.silsagusi.joonggaemoa.domain.article.service.ArticleService;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/api/naverbudongsan/articles")
    public ResponseEntity<ApiResponse<List<ArticleResponse>>> getArticles() {
        List<ArticleResponse> articleResponseList = articleService.getArticle().stream()
                .map(article -> new ArticleResponse()).toList();
        return ResponseEntity.ok(ApiResponse.ok(articleResponseList));
    }

}

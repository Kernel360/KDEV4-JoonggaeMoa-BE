package org.silsagusi.joonggaemoa.domain.article.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.article.repository.ArticleRepository;
import org.silsagusi.joonggaemoa.request.naverland.client.dto.ClientArticleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<ClientArticleResponse.ArticleList> getArticle() {
        return articleRepository.findAll();
    }
}

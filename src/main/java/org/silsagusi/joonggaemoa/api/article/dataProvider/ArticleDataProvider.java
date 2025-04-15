package org.silsagusi.joonggaemoa.api.article.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.api.article.entity.Article;
import org.silsagusi.joonggaemoa.api.article.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleDataProvider {

    private final ArticleRepository articleRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveArticles(List<Article> articles) {
        articleRepository.saveAll(articles);
    }
}

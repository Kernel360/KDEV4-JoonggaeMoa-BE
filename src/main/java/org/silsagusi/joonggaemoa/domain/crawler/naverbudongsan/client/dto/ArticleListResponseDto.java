package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class ArticleListResponseDto {
    private String result;
    private ArticleData data;

    public Collection<Object> getArticles() {
        return Collections.singleton(data.getArticleList());
    }

    // 내부 클래스로 data 객체 정의
    @Data
    public static class ArticleData {
        @JsonProperty("body")
        private List<ArticleDto> articleList;
    }
}

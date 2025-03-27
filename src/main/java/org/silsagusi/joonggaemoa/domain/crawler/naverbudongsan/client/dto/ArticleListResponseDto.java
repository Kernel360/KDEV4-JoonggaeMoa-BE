package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticleListResponseDto {
    private String result;
    private ArticleData data;

    // 내부 클래스로 data 객체 정의
    @Data
    public static class ArticleData {
        @JsonProperty("body")
        private List<ArticleDto> articleList;
    }
}

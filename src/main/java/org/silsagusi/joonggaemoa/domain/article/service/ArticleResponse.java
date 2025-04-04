package org.silsagusi.joonggaemoa.domain.article.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private Long id;
    private String name;
    private String type;
    private String confirmedAt;

    public static ArticleResponse of(ArticleResponse articleResponse) {
        return ArticleResponse.builder()
                .id(articleResponse.getId())
                .name(articleResponse.getName())
                .type(articleResponse.getType())
                .confirmedAt(articleResponse.getConfirmedAt())
                .build();
    }

}

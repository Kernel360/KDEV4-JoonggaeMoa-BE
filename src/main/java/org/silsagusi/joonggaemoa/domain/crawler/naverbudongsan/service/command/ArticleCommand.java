package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity.Article;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCommand {
    private Long id;
    private String name;
    private String type;
    private String confirmedAt;
    
    public static ArticleCommand of(Article article) {
        return ArticleCommand.builder()
                .id(article.getId())
                .name(article.getAtclNm())
                .type(article.getRletTpNm())
                .confirmedAt(article.getAtclCfmYmd())
                .build();
    }

}

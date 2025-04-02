package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.service.command.ArticleCommand;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {
    private Long id;
    private String name;
    private String type;
    private String confirmedAt;

    public static ArticleResponse of(ArticleCommand articleCommand) {
        return ArticleResponse.builder()
                .id(articleCommand.getId())
                .name(articleCommand.getName())
                .type(articleCommand.getType())
                .confirmedAt(articleCommand.getConfirmedAt())
                .build();
    }

}

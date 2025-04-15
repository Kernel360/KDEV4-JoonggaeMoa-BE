package org.silsagusi.joonggaemoa.api.article.service.dto;

import lombok.Builder;
import lombok.Getter;
import org.silsagusi.joonggaemoa.api.article.entity.Article;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ArticleResponse {
    private Long id;
    private String cortarNo;
    private String articleNo;
    private String name;
    private String realEstateType;
    private String tradeType;
    private String price;
    private Integer rentPrice;
    private LocalDate confirmedAt;
    private String lotAddressName;
    private String roadAddressName;
    private String zipCode;
    private String imageUrl;
    private String direction;
    private List<String> tags;
    private String subwayInfo;
    private String companyId;
    private String companyName;
    private String agentName;
    private String cortarName;

    public static ArticleResponse of(Article article) {
        return ArticleResponse.builder()
            .id(article.getId())
            .cortarNo(article.getCortarNo())
            .articleNo(article.getArticleNo())
            .name(article.getName())
            .realEstateType(article.getRealEstateType())
            .tradeType(article.getTradeType())
            .price(article.getPrice() + "")
            .rentPrice(article.getRentPrice())
            .lotAddressName(article.getLotAddressName())
            .roadAddressName(article.getRoadAddressName())
            .zipCode(article.getZoneNo())
            .confirmedAt(article.getConfirmedAt())
            .lotAddressName(article.getLotAddressName())
            .roadAddressName(article.getRoadAddressName())
            .zipCode(article.getZoneNo())
            .imageUrl(article.getImageUrl())
            .direction(article.getDirection())
            .tags(article.getTags())
            .subwayInfo(article.getSubwayInfo())
            .companyId(article.getCompanyId())
            .companyName(article.getCompanyName())
            .agentName(article.getAgentName())
            .cortarName(article.getRegion().getCortarName())
            .build();
    }
}
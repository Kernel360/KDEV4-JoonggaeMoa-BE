package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "articles")
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false)
    private Long id;

    // article name 건물 이름
    private String atclNm;

    // 법정동 코드
    private String cortarNo;

    // real estate type name 매물 유형
    private String rletTpNm;

    // trade type name 거래 유형 (매매, 전세, 월세, 단기임대)
    private String tradTpNm;

    // hangul price
    private String hanPrc;

    // article confirmation date
    private String atclCfmYmd;

    public Article(String atclNm, String cortarNo, String rletTpNm, String tradTpNm, String hanPrc, String atclCfmYmd) {
        this.atclNm = atclNm;
        this.cortarNo = cortarNo;
        this.rletTpNm = rletTpNm;
        this.tradTpNm = tradTpNm;
        this.hanPrc = hanPrc;
        this.atclCfmYmd = atclCfmYmd;
    }
}
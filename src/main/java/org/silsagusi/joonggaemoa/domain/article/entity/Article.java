package org.silsagusi.joonggaemoa.domain.article.entity;

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
    @Column(name = "name")
    private String atclNm;

    // real estate type name 건물 유형 (매물유형) (아파트, 오피스텔 등)
    @Column(name = "type")
    private String rletTpNm;

    // trade type name 거래 유형 (매매, 전세, 월세, 단기임대)
    @Column(name = "trade_type")
    private String tradTpNm;

    // hangul price (2억 8000)
    @Column(name = "price")
    private String hanPrc;

    // article confirmation date
    @Column(name = "confirmed_at")
    private String atclCfmYmd;

    // 위도
    @Column(name = "latitude")
    private Double lat;

    // 경도
    @Column(name = "longitude")
    private Double lng;

    public Article(

            //
            String atclNm,

            //
            String rletTpNm,

            //
            String tradTpNm,

            //
            String hanPrc,

            //
            String atclCfmYmd,

            //
            Double lat,

            //
            Double lng
    ) {

        //
        this.atclNm = atclNm;

        //
        this.rletTpNm = rletTpNm;

        //
        this.tradTpNm = tradTpNm;

        //
        this.hanPrc = hanPrc;

        //
        this.atclCfmYmd = atclCfmYmd;

        //
        this.lat = lat;

        //
        this.lng = lng;
    }
}
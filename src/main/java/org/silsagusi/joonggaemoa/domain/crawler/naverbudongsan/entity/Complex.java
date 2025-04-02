package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity(name = "complexes")
public class Complex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complex_id")
    private Long id;

    // house complex name 단지명
    @Column(name = "name")
    private String hscpNm;

    // house type name 유형 (다세대, 연립)
    @Column(name = "type")
    private String hscpTypeNm;

    // use approval date 준공일
    @Column(name = "approved_at")
    private String useAprvYmd;



    public Complex(

            // house complex name 단지명
            String hscpNm,

            // house type name 유형 (다세대, 연립)
            String hscpTypeNm,

            // use approval date 준공일
            String useAprvYmd
    ) {

        // house complex name 단지명
        this.hscpNm = hscpNm;

        // house type name 유형 (다세대, 연립)
        this.hscpTypeNm = hscpTypeNm;

        // use approval date 준공일
        this.useAprvYmd = useAprvYmd;
    }
}
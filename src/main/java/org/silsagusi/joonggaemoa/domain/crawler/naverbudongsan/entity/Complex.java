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

    private String hscpNm; // 단지명

    private String hscpTypeNm; // 유형 (다세대, 연립)

    private String useAprvYmd; // 준공일

    public Complex(String hscpNm, String hscpTypeNm, String useAprvYmd) {
        this.hscpNm = hscpNm;
        this.hscpTypeNm = hscpTypeNm;
        this.useAprvYmd = useAprvYmd;
    }
}
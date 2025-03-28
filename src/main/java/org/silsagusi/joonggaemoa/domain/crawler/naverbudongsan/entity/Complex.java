package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@ToString
@Entity(name = "complexes")
public class Complex {

    @Id
    @Column(name = "hscp_no")
    private Long hscpNo;

    @Column(name = "hscp_nm")
    private String hscpNm;

    @Column(name = "hscp_type_cd")
    private String hscpTypeCd;

    @Column(name = "hscp_type_nm")
    private String hscpTypeNm;

    @Column(name = "tot_dong_cnt")
    private int totDongCnt;

    @Column(name = "tot_hseh_cnt")
    private int totHsehCnt;

    @Column(name = "gen_hseh_cnt")
    private int genHsehCnt;

    @Column(name = "use_aprv_ymd")
    private String useAprvYmd;

    @Column(name = "rep_img_url")
    private String repImgUrl;

    @Column(name = "deal_cnt")
    private int dealCnt;

    @Column(name = "lease_cnt")
    private int leaseCnt;

    @Column(name = "rent_cnt")
    private int rentCnt;

    @Column(name = "strm_rent_cnt")
    private int strmRentCnt;

    @Column(name = "total_atcl_cnt")
    private int totalAtclCnt;

    @Column(name = "min_spc")
    private BigDecimal minSpc;

    @Column(name = "max_spc")
    private BigDecimal maxSpc;

    @Column(name = "deal_prc_min")
    private int dealPrcMin;

    @Column(name = "deal_prc_max")
    private int dealPrcMax;

    @Column(name = "lease_prc_min")
    private int leasePrcMin;

    @Column(name = "lease_prc_max")
    private int leasePrcMax;

    @Column(name = "rent_prc_min")
    private int rentPrcMin;

    @Column(name = "rent_prc_max")
    private int rentPrcMax;

}

package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@ToString
@Entity(name = "articles")
public class Article {

    @Id
    @Column(name = "atcl_no")
    private Long atclNo;

    @Column(name = "vrfc_tp_cd")
    private String vrfcTpCd;

    @Column(name = "atcl_nm")
    private String atclNm;

    @Column(name = "bild_nm")
    private String bildNm;

    @Column(name = "trad_tp_nm")
    private String tradTpNm;

    @Column(name = "spc1")
    private BigDecimal spc1;

    @Column(name = "spc2")
    private BigDecimal spc2;

    @Column(name = "flr_info")
    private String flrInfo;

    @Column(name = "atcl_fetr_desc")
    private String atclFetrDesc;

    @Column(name = "cfm_ymd")
    private String cfmYmd;

    @Column(name = "prc_info")
    private String prcInfo;

    @Column(name = "same_addr_cnt")
    private int sameAddrCnt;

    @Column(name = "same_addr_direct_cnt")
    private int sameAddrDirectCnt;

    @Column(name = "same_addr_max_prc")
    private String sameAddrMaxPrc;

    @Column(name = "same_addr_min_prc")
    private String sameAddrMinPrc;

    @Column(name = "trad_cmpl_yn")
    private String tradCmplYn;

    @Column(name = "atcl_stat_cd")
    private String atclStatCd;

    @Column(name = "cpid")
    private String cpid;

    @Column(name = "cp_cnt")
    private int cpCnt;

    @Column(name = "rltr_nm")
    private String rltrNm;

    @Column(name = "direction")
    private String direction;

}
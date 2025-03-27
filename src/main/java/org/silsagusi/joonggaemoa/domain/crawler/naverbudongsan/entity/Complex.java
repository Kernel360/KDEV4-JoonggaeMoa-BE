package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "complexes")
public class Complex {

    @Id
    @Column(name = "hscp_no", nullable = false, unique = true)
    private String hscpNo;

    @Column(name = "hscp_nm")
    private String hscpNm;

    @Column(name = "hscp_type_cd")
    private String hscpTypeCd;

    @Column(name = "hscp_type_nm")
    private String hscpTypeNm;

    @Column(name = "tot_dong_cnt")
    private Integer totDongCnt;

    @Column(name = "tot_hseh_cnt")
    private Integer totHsehCnt;

    @Column(name = "gen_hseh_cnt")
    private Integer genHsehCnt;

    @Column(name = "use_aprv_ymd")
    private String useAprvYmd;

    @Column(name = "deal_cnt")
    private Integer dealCnt;

    @Column(name = "lease_cnt")
    private Integer leaseCnt;

    @Column(name = "rent_cnt")
    private Integer rentCnt;

    @Column(name = "strm_rent_cnt")
    private Integer strmRentCnt;

    @Column(name = "total_atcl_cnt")
    private Integer totalAtclCnt;

    @Column(name = "min_spc")
    private String minSpc;

    @Column(name = "max_spc")
    private String maxSpc;

    @Column(name = "isale_prc_min")
    private String isalePrcMin;

    @Column(name = "isale_prc_max")
    private String isalePrcMax;

    @Column(name = "isale_notif_seq")
    private String isaleNotifSeq;

    @Column(name = "isale_sche_label")
    private String isaleScheLabel;

    @Column(name = "isale_sche_label_pre")
    private String isaleScheLabelPre;

    @Column(name = "tour_exist")
    private boolean tourExist;

    @Column(name = "is_seismic")
    private boolean isSeismic;

    @Column(name = "total_elevator_count")
    private Integer totalElevatorCount;
}

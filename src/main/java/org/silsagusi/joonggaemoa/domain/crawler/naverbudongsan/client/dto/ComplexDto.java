package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ComplexDto {

    @JsonProperty("hscpNo")
    private String hscpNo;

    @JsonProperty("hscpNm")
    private String hscpNm;

    @JsonProperty("hscpTypeCd")
    private String hscpTypeCd;

    @JsonProperty("hscpTypeNm")
    private String hscpTypeNm;

    @JsonProperty("totDongCnt")
    private Integer totDongCnt;

    @JsonProperty("totHsehCnt")
    private Integer totHsehCnt;

    @JsonProperty("genHsehCnt")
    private Integer genHsehCnt;

    @JsonProperty("useAprvYmd")
    private String useAprvYmd;

    @JsonProperty("dealCnt")
    private Integer dealCnt;

    @JsonProperty("leaseCnt")
    private Integer leaseCnt;

    @JsonProperty("rentCnt")
    private Integer rentCnt;

    @JsonProperty("strmRentCnt")
    private Integer strmRentCnt;

    @JsonProperty("totalAtclCnt")
    private Integer totalAtclCnt;

    @JsonProperty("minSpc")
    private String minSpc;

    @JsonProperty("maxSpc")
    private String maxSpc;

    @JsonProperty("isalePrcMin")
    private String isalePrcMin;

    @JsonProperty("isalePrcMax")
    private String isalePrcMax;

    @JsonProperty("isaleNotifSeq")
    private String isaleNotifSeq;

    @JsonProperty("isaleScheLabel")
    private String isaleScheLabel;

    @JsonProperty("isaleScheLabelPre")
    private String isaleScheLabelPre;

    @JsonProperty("tourExist")
    private boolean tourExist;

    @JsonProperty("isSeismic")
    private boolean isSeismic;

    @JsonProperty("totalElevatorCount")
    private Integer totalElevatorCount;
}

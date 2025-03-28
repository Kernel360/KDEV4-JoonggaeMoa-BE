package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComplexResponseDto {
    private List<CL> result;
    private boolean hasPaidPreSale;
    private boolean more;
    private boolean isPreSale;

    @Data
    public static class CL {
        private String hscpNo;
        private String hscpNm;
        private String hscpTypeCd;
        private String hscpTypeNm;
        private int totDongCnt;
        private int totHsehCnt;
        private int genHsehCnt;
        private String useAprvYmd;
        private int dealCnt;
        private int leaseCnt;
        private int rentCnt;
        private int strmRentCnt;
        private int totalAtclCnt;
        private String minSpc;
        private String maxSpc;
        private String isalePrcMin;
        private String isalePrcMax;
        private String isaleNotifSeq;
        private String isaleScheLabel;
        private String isaleScheLabelPre;
        private boolean tourExist;
        private boolean isSeismic;
        private int totalElevatorCount;
        private String leasePrcMin;
        private String leasePrcMax;
        private String dealPrcMin;
        private String dealPrcMax;
    }
}

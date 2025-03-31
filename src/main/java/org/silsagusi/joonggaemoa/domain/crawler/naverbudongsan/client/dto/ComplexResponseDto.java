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
        private String hscpNo; // house complex number 단지번호
        private String hscpNm; // house complex name 단지이름
        private String hscpTypeCd; // house complex type code
        private String hscpTypeNm; // house complex type name
        private int totDongCnt; // total dong count
        private int totHsehCnt; // total house eh? count
        private int genHsehCnt; // generated? house eh? count
        private String useAprvYmd; // use approval date
        private int dealCnt; // deal count
        private int leaseCnt;
        private int rentCnt;
        private int strmRentCnt; // strm? rent count
        private int totalAtclCnt; // total article count
        private String minSpc; // minimum space
        private String maxSpc; // maximum space
        private String isalePrcMin; // isale? price minimum
        private String isalePrcMax; // isale? price maximum
        private String isaleNotifSeq; // isale? notification sequence
        private String isaleScheLabel; // isale? schedule label
        private String isaleScheLabelPre; // isale? schedule label pre?
        private boolean tourExist;
        private boolean isSeismic;
        private int totalElevatorCount;
    }
}

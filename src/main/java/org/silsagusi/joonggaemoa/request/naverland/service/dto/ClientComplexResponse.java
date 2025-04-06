package org.silsagusi.joonggaemoa.request.naverland.service.dto;

import lombok.Data;
import org.silsagusi.joonggaemoa.domain.article.service.ComplexResponse;

import java.util.List;

@Data
public class ClientComplexResponse {

    private List<ComplexList> result;

    private boolean hasPaidPreSale;

    private boolean more;

    private boolean isPreSale;

    @Data
    public static class ComplexList {

        // house complex number 단지번호
        private String hscpNo;

        // house complex name 단지명
        private String hscpNm;

        // house complex type code 단지종류코드 (A06, B02)
        private String hscpTypeCd;

        // house complex type name 단지종류 (다세대)
        private String hscpTypeNm;

        // total dong count 동수
        private Integer totDongCnt;

        // total household count 세대수
        private Integer totHsehCnt;

        // generated? household count ? (대부분 0)
        private Integer genHsehCnt;

        // use approval date 준공날짜
        private String useAprvYmd;

        // deal count 매매물건수
        private Integer dealCnt;

        // lease count 전세물건수
        private Integer leaseCnt;

        // rental count 월세물건수
        private Integer rentCnt;

        // short-term rental count 단기임대물건수
        private Integer strmRentCnt;

        // total article count 전체물건수
        private Integer totalAtclCnt;

        // minimum space 최소평수 [제곱미터]
        private String minSpc;

        // maximum space 최대평수 [제곱미터]
        private String maxSpc;

        private String isalePrcMin;

        private String isalePrcMax;

        private String isaleNotifSeq;

        private String isaleScheLabel;

        private String isaleScheLabelPre;

        // 임장여부
        private Boolean tourExist;

        // 내진설계여부
        private Boolean isSeismic;

        // 엘리베이터 대수
        private Integer totalElevatorCount;
    }
}

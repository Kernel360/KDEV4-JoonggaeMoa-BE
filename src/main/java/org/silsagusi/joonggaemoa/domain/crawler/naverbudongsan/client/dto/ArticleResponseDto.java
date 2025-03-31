package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleResponseDto {
    private String code;
    private boolean hasPaidPreSale;
    private boolean more;
    private boolean TIME;
    private int z;
    private int page;
    private List<AL> body;

    @Data
    public static class AL {
        private String atclNo; // article number
        private String cortarNo; // 법정동코드
        private String atclNm; // article name
        private String rletTpCd; // real estate type code 매물유형코드 (A1, B3 등)
        private String uprRletTpCd; // 상위 real estate type code
        private String rletTpNm; // real estate type name
        private String tradTpCd; // trade type code
        private String tradTpNm; // trade type name
        private String vrfcTpCd; // VR fc? type code
        private int prc; // price 매매가 / 전세
        private int rentPrc; // rent price 월세
        private String hanPrc; // hangul price
        private String direction; // 방향 (남향, 북향 등)
        private String atclCfmYmd; // article confirmation date
//        private String repImgUrl; // representative image url
//        private String repImgTpCd; // representative image type code
//        private String repImgThumb; // representative image thumbnail
        private double lat; // latitude
        private double lng; // longitude
//        private String atclFetrDesc; // article fetr? description
        private List<String> tagList;
        private String cpid; // company id
        private String cpNm; // company name
        private String rltrNm; // real estate agent name
        private String sbwyInfo; // subway info
        private String tradePriceHan; // trade price in 한글
        private int tradeRentPrice;
        private boolean tradeCheckedByOwner; // 본인인증완료여부
        private boolean isVrExposed; // VR촬영완료여부
    }
}

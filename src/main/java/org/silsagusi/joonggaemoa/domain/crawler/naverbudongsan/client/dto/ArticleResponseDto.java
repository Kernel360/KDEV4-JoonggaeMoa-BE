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
        private String atclNo;
        private String cortarNo;
        private String atclNm;
        private String atclStatCd;
        private String rletTpCd;
        private String uprRletTpCd;
        private String rletTpNm;
        private String tradTpCd;
        private String tradTpNm;
        private String vrfcTpCd;
        private String flrInfo;
        private int prc;
        private int rentPrc;
        private String hanPrc;
        private String spc1;
        private String spc2;
        private String direction;
        private String atclCfmYmd;
        private String repImgUrl;
        private String repImgTpCd;
        private String repImgThumb;
        private double lat;
        private double lng;
        private List<String> tagList;
    }
}

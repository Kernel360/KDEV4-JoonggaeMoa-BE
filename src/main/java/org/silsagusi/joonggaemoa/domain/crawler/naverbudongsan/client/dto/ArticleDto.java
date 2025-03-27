package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArticleDto {

    @JsonProperty("atclNo")
    private String atclNo;

    @JsonProperty("cortarNo")
    private String cortarNo;

    @JsonProperty("atclNm")
    private String atclNm;

    @JsonProperty("rletTpCd")
    private String rletTpCd;

    @JsonProperty("uprRletTpCd")
    private String uprRletTpCd;

    @JsonProperty("rletTpNm")
    private String rletTpNm;

    @JsonProperty("tradTpCd")
    private String tradTpCd;

    @JsonProperty("tradTpNm")
    private String tradTpNm;

    @JsonProperty("vrfcTpCd")
    private String vrfcTpCd;

    @JsonProperty("prc")
    private int prc;

    @JsonProperty("rentPrc")
    private int rentPrc;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("repImgUrl")
    private String repImgUrl;

    @JsonProperty("repImgTpCd")
    private String repImgTpCd;

    @JsonProperty("repImgThumb")
    private String repImgThumb;

    // 위도
    @JsonProperty("lat")
    private double lat;

    // 경도
    @JsonProperty("lng")
    private double lng;

    // 태그
    @JsonProperty("tagList")
    private List<String> tagList;

    @JsonProperty("minute")
    private int minute;

    // 동일 매물 수
    @JsonProperty("sameAddrCnt")
    private int sameAddrCnt;

    @JsonProperty("sameAddrDirectCnt")
    private int sameAddrDirectCnt;

    @JsonProperty("cpid")
    private String cpid;

    @JsonProperty("cpNm")
    private String cpNm;

    @JsonProperty("cpCnt")
    private int cpCnt;

    @JsonProperty("minMviFee")
    private int minMviFee;

    @JsonProperty("maxMviFee")
    private int maxMviFee;

    @JsonProperty("sbwyInfo")
    private String sbwyInfo;

    @JsonProperty("svcCont")
    private String svcCont;

    @JsonProperty("gdrNm")
    private String gdrNm;

    @JsonProperty("etRoomCnt")
    private int etRoomCnt;

    @JsonProperty("tradePriceHan")
    private String tradePriceHan;

    @JsonProperty("tradeRentPrice")
    private int tradeRentPrice;

    // 확인 매물 여부
    @JsonProperty("tradeCheckedByOwner")
    private boolean tradeCheckedByOwner;

    // VR 촬영 여부
    @JsonProperty("isVrExposed")
    private boolean isVrExposed;

}

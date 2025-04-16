package org.silsagusi.batch.naverland.service.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.silsagusi.core.domain.article.Article;
import org.silsagusi.core.domain.article.Region;

import lombok.Data;

@Data
public class ClientArticleResponse {

	private String code;

	private boolean hasPaidPreSale;

	private boolean more;

	private boolean TIME;

	private Integer z;

	private Integer page;

	private List<Body> body;

	@Data
	public static class Body {

		// article number
		private String atclNo;

		// 법정동코드
		private String cortarNo;

		// article name
		private String atclNm;

		// real estate type code 매물유형코드 (A1, B3 등)
		private String rletTpCd;

		// 상위 real estate type code
		private String uprRletTpCd;

		// real estate type name
		private String rletTpNm;

		// trade type code
		private String tradTpCd;

		// trade type name
		private String tradTpNm;

		// VR facility? type code  (NONE, S_VR)
		private String vrfcTpCd;

		// price 매매가/보증금
		private Integer prc;

		// rent price 월세
		private Integer rentPrc;

		// hangul price 매매가/보증금 한글로
		private String hanPrc;

		// 방향 (남향, 북향 등)
		private String direction;

		// article confirmation date
		private String atclCfmYmd;

		// representative image url
		private String repImgUrl;

		// representative image type code
		private String repImgTpCd;

		// representative image thumbnail url
		private String repImgThumb;

		// 위도
		private Double lat;

		// 경도
		private Double lng;

		// article fetr? description
		private String atclFetrDesc;

		// 태그 리스트
		private List<String> tagList;

		// company id
		private String cpid;

		// company name
		private String cpNm;

		// real estate agent name
		private String rltrNm;

		// subway info
		private String sbwyInfo;

		// trade price in hangul
		private String tradePriceHan;

		// trade rent price
		private Integer tradeRentPrice;

		// 본인인증완료여부
		private Boolean tradeCheckedByOwner;

		// VR촬영완료여부
		private Boolean isVrExposed;
	}

	public static Article createFrom(ClientArticleResponse.Body body, Region region, AddressResponse addressResponse) {
		return new Article(
			body.getCortarNo(),
			body.getAtclNo(),
			body.getAtclNm(),
			body.getRletTpNm(),
			body.getTradTpNm(),
			body.getPrc(),
			body.getRentPrc(),
			parseConfirmedAt(body.getAtclCfmYmd()),
			body.getLat(),
			body.getLng(),
			body.getRepImgUrl(),
			body.getDirection(),
			body.getTagList(),
			body.getSbwyInfo(),
			body.getCpid(),
			body.getCpNm(),
			body.getRltrNm(),
			region,
			addressResponse.getLotAddress(),
			addressResponse.getRoadAddress(),
			addressResponse.getCity(),
			addressResponse.getDistrict(),
			addressResponse.getRegion(),
			addressResponse.getMainAddressNo(),
			addressResponse.getSubAddressNo(),
			addressResponse.getRoadName(),
			addressResponse.getMainBuildingNo(),
			addressResponse.getSubBuildingNo(),
			addressResponse.getBuildingName(),
			addressResponse.getZoneNo()
		);
	}

	private static LocalDate parseConfirmedAt(String dateStr) {
		if (dateStr == null || dateStr.isEmpty())
			return null;
		try {
			return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yy.MM.dd."));
		} catch (Exception e) {
			try {
				return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
			} catch (Exception ignored) {
				return null;
			}
		}
	}
}
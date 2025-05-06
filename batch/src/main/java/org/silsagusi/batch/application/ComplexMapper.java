package org.silsagusi.batch.application;

import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Component;

@Component
public class ComplexMapper {

	public Complex toEntity(NaverLandComplexResponse.NaverLandComplex naverLandComplex, Region region) {
		return Complex.create(
			naverLandComplex.getHscpNo(),                   // complexCode
			naverLandComplex.getHscpNm(),                   // complexName
			naverLandComplex.getHscpTypeCd(),               // buildingTypeCode
			naverLandComplex.getHscpTypeNm(),               // buildingType
			naverLandComplex.getTotDongCnt(),               // countDong
			naverLandComplex.getTotHsehCnt(),               // countHousehold
			naverLandComplex.getGenHsehCnt(),               // countHouseholdGeneral
			naverLandComplex.getUseAprvYmd(),               // confirmedAt
			naverLandComplex.getDealCnt(),                  // countDeal
			naverLandComplex.getLeaseCnt(),                 // countLease
			naverLandComplex.getRentCnt(),                  // countRent
			naverLandComplex.getStrmRentCnt(),              // countRentShortTerm
			naverLandComplex.getTotalAtclCnt(),             // countArticles
			naverLandComplex.getMinSpc(),                   // sizeMin
			naverLandComplex.getMaxSpc(),                   // sizeMax
			Integer.valueOf(naverLandComplex.getIsalePrcMin()),// priceSaleInitialMin
			Integer.valueOf(naverLandComplex.getIsalePrcMax()),// priceSaleInitialMax
			Boolean.FALSE,                     // tourExists
			Boolean.FALSE,                     // isSeismic
			naverLandComplex.getTotalElevatorCount(),       // countElevator
			region
		);
	}

	public Complex toEntity(ZigBangDanjiResponse.ZigBangDanji dto, Region region) {
		return Complex.create(
			String.valueOf(dto.getId()),
			dto.getName(),          // complexName
			null,                   // buildingTypeCode
			dto.getReal_type(),     // buildingType
			null,                   // countDong
			dto.get총세대수(),        // countHousehold
			null,                   // countHouseholdGeneral
			dto.get사용승인일(),       // confirmedAt
			null,                   // countDeal
			null,                   // countLease
			null,                   // countRent
			null,                   // countRentShortTerm
			null,                   // countArticles
			null,                   // sizeMin
			null,                   // sizeMax
			(dto.getPrice() != null && dto.getPrice().getSales() != null && dto.getPrice().getSales().getMin() != null
				? dto.getPrice().getSales().getMin() : 0), // priceSaleInitialMin
			(dto.getPrice() != null && dto.getPrice().getSales() != null && dto.getPrice().getSales().getMax() != null
				? dto.getPrice().getSales().getMax() : 0), // priceSaleInitialMax
			Boolean.FALSE,          // tourExists
			Boolean.FALSE,          // isSeismic
			null,                   // countElevator
			region
		);
	}
}

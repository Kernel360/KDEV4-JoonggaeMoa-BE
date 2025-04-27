package org.silsagusi.batch.infrastructure.dataProvider;

import java.util.List;

import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplexDataProvider {

	private final ComplexRepository complexRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveComplexes(List<Complex> complexes) {
		complexRepository.saveAll(complexes);
	}

	public static Complex createNaverLandComplex(
		NaverLandComplexResponse.NaverLandComplex dto, Region region) {
		return Complex.create(
			dto.getHscpNo(),                   // complexCode
			dto.getHscpNm(),                   // complexName
			dto.getHscpTypeCd(),               // buildingTypeCode
			dto.getHscpTypeNm(),               // buildingType
			dto.getTotDongCnt(),               // countDong
			dto.getTotHsehCnt(),               // countHousehold
			dto.getGenHsehCnt(),               // countHouseholdGeneral
			dto.getUseAprvYmd(),               // confirmedAt
			dto.getDealCnt(),                  // countDeal
			dto.getLeaseCnt(),                 // countLease
			dto.getRentCnt(),                  // countRent
			dto.getStrmRentCnt(),              // countRentShortTerm
			dto.getTotalAtclCnt(),             // countArticles
			dto.getMinSpc(),                   // sizeMin
			dto.getMaxSpc(),                   // sizeMax
			Integer.valueOf(dto.getIsalePrcMin()),// priceSaleInitialMin
			Integer.valueOf(dto.getIsalePrcMax()),// priceSaleInitialMax
			Boolean.FALSE,                     // tourExists
			Boolean.FALSE,                     // isSeismic
			dto.getTotalElevatorCount(),       // countElevator
			region
		);
	}

	public static Complex createZigBangDanji(
		ZigBangDanjiResponse.ZigBangDanji dto, Region region) {
		return Complex.create(
			null,       // complexCode
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

package org.silsagusi.batch.infrastructure.dataprovider;

import java.util.List;

import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.batch.naverland.infrastructure.dto.NaverLandComplexResponse;
import org.silsagusi.batch.zigbang.infrastructure.dto.ZigBangDanjiResponse;
import org.silsagusi.core.domain.article.Complex;
import org.silsagusi.core.domain.article.Region;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ComplexDataProvider {

	private final ComplexRepository complexRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveComplexes(List<Complex> complexes) {
		complexRepository.saveAll(complexes);
	}

	public Complex createNaverLandComplex(
		NaverLandComplexResponse.NaverLandComplex naverLandComplex, Region region) {
		return Complex.create(
			naverLandComplex.getHscpNo(),                   // complexCode
			naverLandComplex.getHscpNm(),                   // complexName
			naverLandComplex.getTotDongCnt(),               // countDong
			naverLandComplex.getTotHsehCnt(),               // countHousehold
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

	public Complex createZigBangDanji(
		ZigBangDanjiResponse.ZigBangDanji zigBangDanji, Region region) {
		return Complex.create(
			zigBangDanji.getId().toString(), // complexCode
			zigBangDanji.getName(),   // complexName
			null,                     // countDong
			zigBangDanji.get총세대수(), // countHousehold
			zigBangDanji.get사용승인일(),// confirmedAt
			null,                     // countDeal
			null,                     // countLease
			null,                     // countRent
			null,                     // countRentShortTerm
			null,                     // countArticles
			null,                     // sizeMin
			null,                     // sizeMax
			(zigBangDanji.getPrice() != null && zigBangDanji.getPrice().getSales() != null && zigBangDanji.getPrice().getSales().getMin() != null
				? zigBangDanji.getPrice().getSales().getMin() : 0), // priceSaleInitialMin
			(zigBangDanji.getPrice() != null && zigBangDanji.getPrice().getSales() != null && zigBangDanji.getPrice().getSales().getMax() != null
				? zigBangDanji.getPrice().getSales().getMax() : 0), // priceSaleInitialMax
			Boolean.FALSE,          // tourExists
			Boolean.FALSE,          // isSeismic
			null,                   // countElevator
			region
		);
	}
}

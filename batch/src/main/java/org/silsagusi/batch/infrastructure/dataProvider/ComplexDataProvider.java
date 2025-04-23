package org.silsagusi.batch.infrastructure.dataProvider;

import lombok.RequiredArgsConstructor;
import org.silsagusi.batch.infrastructure.repository.ComplexRepository;
import org.silsagusi.batch.scrape.naverland.service.dto.NaverLandComplexResponse;
import org.silsagusi.core.domain.article.Complex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexDataProvider {

	private final ComplexRepository complexRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveComplexes(List<Complex> complexes) {
		complexRepository.saveAll(complexes);
	}

	public Complex createNaverLandComplex(
		NaverLandComplexResponse.NaverLandComplex naverLandComplex
	) {
		Complex complex = new Complex(
			naverLandComplex.getHscpNo(),
			naverLandComplex.getHscpNm(),
			naverLandComplex.getHscpTypeCd(),
			naverLandComplex.getHscpTypeNm(),
			naverLandComplex.getTotDongCnt(),
			naverLandComplex.getTotHsehCnt(),
			naverLandComplex.getGenHsehCnt(),
			naverLandComplex.getUseAprvYmd(),
			naverLandComplex.getDealCnt(),
			naverLandComplex.getLeaseCnt(),
			naverLandComplex.getRentCnt(),
			naverLandComplex.getStrmRentCnt(),
			naverLandComplex.getTotalAtclCnt(),
			naverLandComplex.getMinSpc(),
			naverLandComplex.getMaxSpc(),
			naverLandComplex.getIsalePrcMin(),
			naverLandComplex.getIsalePrcMax(),
			naverLandComplex.getTourExist(),
			naverLandComplex.getIsSeismic(),
			naverLandComplex.getTotalElevatorCount()
		);
			return complex;
	}

//	public Complex createZigBangComplex(
//		String complex_code, String complex_name
//		) {
//		Complex complex = new Complex(
//
//		);
//		return complex;
//	}
}
package org.silsagusi.api.article.application.validator;

import org.silsagusi.api.article.application.dto.MarkerFilterRequest;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MarkerFilterValidator {

	public void validateFilter(MarkerFilterRequest request) {
		if (request.getTradeType() != null) {
			for (String tradeType : request.getTradeType()) {
				if (!StringUtils.hasText(tradeType) || !tradeType.matches("^[가-힣]+$")) {
					throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
				}
			}
		}

		if (request.getBuildingTypeCode() != null) {
			for (String code : request.getBuildingTypeCode()) {
				if (!StringUtils.hasText(code) || !code.matches("^[a-zA-Z0-9]+$")) {
					throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
				}
			}
		}

		Long minSalePrice = request.getMinSalePrice();
		Long maxSalePrice = request.getMaxSalePrice();
		if (minSalePrice != null && minSalePrice < 0) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}
		if (maxSalePrice != null && maxSalePrice < 0) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}
		if (minSalePrice != null && maxSalePrice != null && minSalePrice > maxSalePrice) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}

		Long minRentPrice = request.getMinRentPrice();
		Long maxRentPrice = request.getMaxRentPrice();
		if (minRentPrice != null && minRentPrice < 0) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}
		if (maxRentPrice != null && maxRentPrice < 0) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}
		if (minRentPrice != null && maxRentPrice != null && minRentPrice > maxRentPrice) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED, MarkerFilterValidator.class);
		}
	}

}

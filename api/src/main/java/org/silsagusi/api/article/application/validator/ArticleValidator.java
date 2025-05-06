package org.silsagusi.api.article.application.validator;

import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleValidator {

	public boolean isBoundsSearch(Double neLat, Double neLng, Double swLat, Double swLng) {
		return neLat != null && neLng != null && swLat != null && swLng != null;
	}

	public boolean isRegionSearch(String regionPrefix) {
		return regionPrefix != null && !regionPrefix.isBlank();
	}

	public void validateSearchParams(
		List<String> realEstateType, List<String> tradeType,
		String minPrice, String maxPrice, String regionPrefix,
		Double neLat, Double neLng, Double swLat, Double swLng
	) {
		boolean hasBounds = isBoundsSearch(neLat, neLng, swLat, swLng);
		boolean hasRegion = isRegionSearch(regionPrefix);
		boolean hasSpec = (realEstateType != null && !realEstateType.isEmpty())
			|| (tradeType != null && !tradeType.isEmpty())
			|| (minPrice != null && !minPrice.isBlank())
			|| (maxPrice != null && !maxPrice.isBlank());
		if (!hasBounds && !hasRegion && !hasSpec) {
			throw new CustomException(ErrorCode.MISSING_REQUIRED_VALUE);
		}
		if (minPrice != null && maxPrice != null && !minPrice.isBlank() && !maxPrice.isBlank()) {
			try {
				long min = Long.parseLong(minPrice);
				long max = Long.parseLong(maxPrice);
				if (min > max) {
					throw new CustomException(ErrorCode.VALIDATION_FAILED);
				}
			} catch (NumberFormatException e) {
				throw new CustomException(ErrorCode.VALIDATION_FAILED);
			}
		}
	}

	public void validateBoundsParams(Double swLat, Double neLat, Double swLng, Double neLng) {
		if (swLat == null || neLat == null || swLng == null || neLng == null) {
			throw new CustomException(ErrorCode.MISSING_REQUIRED_VALUE);
		}
		if (swLat >= neLat || swLng >= neLng) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED);
		}
	}

	public void validateClusterParams(String clusterId, int precision, int page, int size) {
		if (clusterId == null || clusterId.isBlank()) {
			throw new CustomException(ErrorCode.INVALID_CLUSTER_ID);
		}
		String[] parts = clusterId.split(",");
		if (parts.length != 2) {
			throw new CustomException(ErrorCode.INVALID_CLUSTER_ID);
		}
		try {
			Long.parseLong(parts[0]);
			Long.parseLong(parts[1]);
		} catch (NumberFormatException e) {
			throw new CustomException(ErrorCode.INVALID_CLUSTER_ID);
		}
		if (precision < 0 || page < 0 || size <= 0) {
			throw new CustomException(ErrorCode.VALIDATION_FAILED);
		}
	}

	public void validateTypeParam(String type) {
	    if (type == null || type.isBlank()) {
	        throw new CustomException(ErrorCode.MISSING_REQUIRED_VALUE);
	    }
	    String t = type.toLowerCase();
	    if (!("bounds".equals(t) || "region".equals(t) || "filter".equals(t))) {
	        throw new CustomException(ErrorCode.VALIDATION_FAILED);
	    }
	}
}

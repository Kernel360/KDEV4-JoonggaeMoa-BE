package org.silsagusi.api.article.application.validator;

import lombok.RequiredArgsConstructor;
import org.silsagusi.api.article.infrastructure.dto.BoundingBox;
import org.silsagusi.api.common.exception.CustomException;
import org.silsagusi.api.common.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoundingBoxValidator {

	public void validateBoundingBox(BoundingBox box) {

		if (box.getSwLat() == null
			|| box.getSwLng() == null
			|| box.getNeLat() == null
			|| box.getNeLng() == null) {
			throw new CustomException(ErrorCode.MISSING_REQUIRED_VALUE, BoundingBoxValidator.class);
		}

		if (box.getSwLat() < 33 || box.getSwLat() > 43 || box.getNeLat() < 33 || box.getNeLat() > 43) {
			throw new CustomException(ErrorCode.INVALID_COORDINATE_RANGE, BoundingBoxValidator.class);
		}

		if (box.getSwLng() < 124 || box.getSwLng() > 132 || box.getNeLng() < 124 || box.getNeLng() > 132) {
			throw new CustomException(ErrorCode.INVALID_COORDINATE_RANGE, BoundingBoxValidator.class);
		}

		if (box.getSwLat() >= box.getNeLat()) {
			throw new CustomException(ErrorCode.INVALID_COORDINATE_ORDER, BoundingBoxValidator.class);
		}

		if (box.getSwLng() >= box.getNeLng()) {
			throw new CustomException(ErrorCode.INVALID_COORDINATE_ORDER, BoundingBoxValidator.class);
		}
	}
}

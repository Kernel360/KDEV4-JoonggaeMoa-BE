package org.silsagusi.batch.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlexibleDateDeserializer extends JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		String date = parser.getText().trim();

		// 불필요한 공백 제거 및 끝의 점(.) 제거
		date = date.replaceAll("\\s+", "").replaceAll("\\.$", "");

		// 월 또는 일이 한 자리인 경우 두 자리로 보정 (예: "2013.10.5" -> "2013.10.05")
		String[] parts = date.split("\\.");
		if (parts.length == 3) {
		    try {
		        int year = Integer.parseInt(parts[0]);
		        int month = Integer.parseInt(parts[1]);
		        int day = Integer.parseInt(parts[2]);
		        date = String.format("%04d.%02d.%02d", year, month, day);
		    } catch (NumberFormatException ignored) {
		        // 파싱 실패 시 원본 문자열 유지
		    }
		}

		// "일자"가 00이면 01로 교체
		date = date.replaceAll("(\\d{4}\\.\\d{2}\\.)00", "$101");

		try {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		} catch (DateTimeParseException e) {
			try {
				DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy.MM");
				YearMonth yearMonth = YearMonth.parse(date, yearMonthFormatter);
				return yearMonth.atDay(1); // 일자가 없으면 1일로 처리
			} catch (DateTimeParseException ex) {
				throw new IOException("날짜 포맷 오류: " + date, ex);
			}
		}
	}
}
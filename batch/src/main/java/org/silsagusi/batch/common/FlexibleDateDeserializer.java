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
		String date = parser.getText();
		try {
			return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd."));
		} catch (DateTimeParseException e) {
			try {
				DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy.MM.");
				YearMonth yearMonth = YearMonth.parse(date, yearMonthFormatter);
				return yearMonth.atDay(1); // 일자가 없으면 1일로 처리
			} catch (DateTimeParseException ex) {
				throw new IOException("Unable to parse date: " + date, ex);
			}
		}
	}
}
package org.silsagusi.core.logger;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			if (obj instanceof Object[]) {
				return Arrays.stream((Object[])obj)
					.map(LogUtil::safeJson)
					.toList()
					.toString();
			}
			return safeJson(obj);
		} catch (Exception e) {
			return String.valueOf(obj);
		}
	}

	private static String safeJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return "[Unserializable: " + obj.getClass().getSimpleName() + "]";
		}
	}

}

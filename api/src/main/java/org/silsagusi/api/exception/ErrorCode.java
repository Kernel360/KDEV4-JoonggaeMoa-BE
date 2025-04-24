package org.silsagusi.api.exception;

import java.util.IllegalFormatException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400X - 클라이언트 오류
	BAD_REQUEST(4000, "잘못된 요청입니다."),
	MISSING_REQUIRED_VALUE(4001, "필수 입력값이 누락되었습니다."),
	INVALID_FILE(4002, "잘못된 파일 형식입니다."),
	VALIDATION_FAILED(4003, "잘못된 데이터 형식입니다."),
	PAYLOAD_TOO_LARGE(4004, "요청 데이터 길이가 초과되었습니다."),
	METHOD_NOT_ALLOWED(4005, "지원하지 않는 HTTP 메서드입니다."),

	// 401X - 인증 오류
	UNAUTHORIZED(4010, "토큰이 없습니다."),
	TOKEN_EXPIRED(4011, "토큰이 만료되었습니다."),
	INVALID_TOKEN(4012, "유효하지 않은 토큰입니다."),
	INVALID_CREDENTIALS(4013, "로그인 정보가 올바르지 않습니다."),

	// 403X - 권한 오류
	FORBIDDEN(4030, "권한이 없습니다."),
	ACCESS_DENIED(4031, "접근이 거부되었습니다."),
	ACCOUNT_DISABLED(4032, "비활성화된 계정입니다."),

	// 404X - 리소스 없음
	NOT_FOUND_END_POINT(4040, "존재하지 않는 엔드포인트입니다."),
	NOT_FOUND_ELEMENT(4041, "존재하지 않는 엔티티입니다."),
	NOT_FOUND_USER(4042, "존재하지 않는 사용자입니다. (%s)"),
	NOT_FOUND_CUSTOMER(4043, "존재하지 않는 고객입니다. (%s)"),

	// 409X - 데이터 충돌
	CONFLICT(4090, "중복된 데이터입니다."),
	CONFLICT_USERNAME(4091, "이미 존재하는 username입니다."),
	CONFLICT_PHONE(4092, "이미 존재하는 phone입니다."),
	CONFLICT_EMAIL(4093, "이미 존재하는 email입니다."),

	// 415X - 미디어 타입 오류
	UNSUPPORTED_MEDIA_TYPE(4150, "지원하지 않는 미디어 타입입니다."),
	CONVERT_ERROR(4151, "Converting 오류입니다."),

	// 500X - 서버 오류
	INTERNAL_SERVER_ERROR(5000, "서버 내부 오류입니다."),
	UNEXPECTED_ERROR(5001, "예상하지 못한 오류가 발생했습니다."),
	DATABASE_ERROR(5002, "데이터베이스 오류가 발생했습니다."),
	EXTERNAL_API_ERROR(5003, "외부 API 연동 중 오류가 발생했습니다."),
	FILE_UPLOAD_ERROR(5004, "파일 업로드에 실패했습니다."),
	CACHE_SERVER_ERROR(5005, "캐시 서버 오류가 발생했습니다."),

	// 503X - 서비스 불가
	SERVICE_UNAVAILABLE(5030, "현재 서비스를 이용할 수 없습니다.");

	private final Integer code;
	private final String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String formatMessage(Object... args) {
		if (args == null || args.length == 0) {
			return message;
		}

		try {
			return String.format(message, args);
		} catch (IllegalFormatException e) {
			return message;
		}
	}
}
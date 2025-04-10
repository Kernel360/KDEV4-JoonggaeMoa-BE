package org.silsagusi.joonggaemoa.global.api.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400X - 클라이언트 오류
	BAD_REQUEST(4000, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	MISSING_REQUIRED_VALUE(4001, HttpStatus.BAD_REQUEST, "필수 입력값이 누락되었습니다."),
	INVALID_FILE(4002, HttpStatus.BAD_REQUEST, "잘못된 파일 형식입니다."),
	VALIDATION_FAILED(4003, HttpStatus.BAD_REQUEST, "잘못된 데이터 형식입니다."),
	PAYLOAD_TOO_LARGE(4004, HttpStatus.BAD_REQUEST, "요청 데이터 길이가 초과되었습니다."),
	METHOD_NOT_ALLOWED(4005, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),

	// 401X - 인증 오류
	UNAUTHORIZED(4010, HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),
	TOKEN_EXPIRED(4011, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
	INVALID_TOKEN(4012, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	INVALID_CREDENTIALS(4013, HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다."),

	// 403X - 권한 오류
	FORBIDDEN(4030, HttpStatus.FORBIDDEN, "권한이 없습니다."),
	ACCESS_DENIED(4031, HttpStatus.FORBIDDEN, "접근이 거부되었습니다."),
	ACCOUNT_DISABLED(4032, HttpStatus.FORBIDDEN, "비활성화된 계정입니다."),

	// 404X - 리소스 없음
	NOT_FOUND_ELEMENT(4040, HttpStatus.NOT_FOUND, "존재하지 않는 엔티티입니다."),
	NOT_FOUND_END_POINT(4041, HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
	NOT_FOUND_USER(4042, HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
	NOT_FOUND_CUSTOMER(4043, HttpStatus.NOT_FOUND, "존재하지 않는 고객입니다."),

	// 409X - 데이터 충돌
	CONFLICT(4090, HttpStatus.CONFLICT, "중복된 데이터입니다."),
	CONFLICT_USERNAME(4091, HttpStatus.CONFLICT, "이미 존재하는 username입니다."),
	CONFLICT_PHONE(4092, HttpStatus.CONFLICT, "이미 존재하는 phone입니다."),
	CONFLICT_EMAIL(4093, HttpStatus.CONFLICT, "이미 존재하는 email입니다."),

	// 415X - 미디어 타입 오류
	UNSUPPORTED_MEDIA_TYPE(4150, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원하지 않는 미디어 타입입니다."),
	CONVERT_ERROR(4151, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Converting 오류입니다."),

	// 500X - 서버 오류
	INTERNAL_SERVER_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
	UNEXPECTED_ERROR(5001, HttpStatus.INTERNAL_SERVER_ERROR, "예상하지 못한 오류가 발생했습니다."),
	DATABASE_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다."),
	EXTERNAL_API_ERROR(5003, HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 연동 중 오류가 발생했습니다."),
	FILE_UPLOAD_ERROR(5004, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
	CACHE_SERVER_ERROR(5005, HttpStatus.INTERNAL_SERVER_ERROR, "캐시 서버 오류가 발생했습니다."),

	// 503X - 서비스 불가
	SERVICE_UNAVAILABLE(5030, HttpStatus.SERVICE_UNAVAILABLE, "현재 서비스를 이용할 수 없습니다.");

	private final Integer code;
	private final HttpStatus httpStatus;
	private final String message;
}
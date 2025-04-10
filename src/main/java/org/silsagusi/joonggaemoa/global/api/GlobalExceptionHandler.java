package org.silsagusi.joonggaemoa.global.api;

import lombok.extern.slf4j.Slf4j;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ApiResponse<?> handleNoPageFoundException(Exception e) {
        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.NOT_FOUND_END_POINT));
    }

    // 커스텀 예외
    @ExceptionHandler(value = {CustomException.class})
    public ApiResponse<?> handleCustomException(CustomException e) {
        log.error("GlobalExceptionHandler catch CustomException : {}", e.getMessage());
        return ApiResponse.fail(e);
    }

    // 기본 예외
    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler catch Exception : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ApiResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("GlobalExceptionHandler catch DataIntegrityViolationException : {}", e.getMessage());
        String message = e.getMostSpecificCause().getMessage();

        if (message.contains("UK_username")) {
            return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_USERNAME));
        } else if (message.contains("UK_phone")) {
            return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_PHONE));
        } else if (message.contains("UK_email")) {
            return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT_EMAIL));
        }

        return ApiResponse.fail(new CustomException(ErrorCode.CONFLICT));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("GlobalExceptionHandler catch MethodArgumentNotValidException : {}", e.getMessage());
        return ApiResponse.fail(new CustomException(ErrorCode.VALIDATION_FAILED));
    }
}

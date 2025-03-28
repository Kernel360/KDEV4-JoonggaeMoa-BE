/*
package org.silsagusi.joonggaemoa.global.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

//@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
		Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		if (returnType.getParameterType() == ApiResponse.class) {
			HttpStatus status = ((ApiResponse<?>)body).getHttpStatus();
			response.setStatusCode(status);
		}

		return body;
	}
}
*/

package org.silsagusi.core.logger;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void controller() {
	}

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void service() {
	}

	@Around("controller()")
	public Object logControllerCall(ProceedingJoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();

		String method = request.getMethod();
		String handler = joinPoint.getSignature().toShortString();
		Map<String, Object> params = new HashMap<>();

		try {
			String decodeURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");

			params.put("uri", decodeURI);
			params.put("method", method);
			params.put("params", URLDecoder.decode(request.getQueryString(), "UTF-8"));
			params.put("request", URLDecoder.decode(request.getRequestURL().toString(), "UTF-8"));
			params.put("user", request.getUserPrincipal().getName());
		} catch (Exception e) {
			log.warn("LoggerAspect Error");
		}

		log.info("[Controller Request] Handler: {}", handler);
		log.info("\tparams: {}", params);

		long start = System.currentTimeMillis();

		Object result = joinPoint.proceed();
		long time = System.currentTimeMillis() - start;

		log.info("[Controller Response] {} {} | Time: {}ms | Response: {}", method, params.get("url"), time,
			LogUtil.toJson(result));
		return result;

	}

	@Before("service()")
	public void beforeService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		log.info("[Service Start] {} | Args: {}", methodName, LogUtil.toJson(args));
	}

	@AfterReturning(pointcut = "service()", returning = "returnValue")
	public void afterReturningService(JoinPoint joinPoint, Object returnValue) {
		String methodName = joinPoint.getSignature().getName();
		log.info("[Service End] {} | Return: {}", methodName, LogUtil.toJson(returnValue));
	}

}
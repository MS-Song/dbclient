package com.song7749.web.config;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.song7749.base.MessageVo;

/**
 * <pre>
 * Class Name : GlobalResponseAdvice.java
 * Description : Response 의 wrapper object 를 추가 한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 8.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 3. 8.
 */

@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// swagger 의 response type 은 bypass
		boolean isSwaggerReqest = false;
		isSwaggerReqest  = isSwaggerReqest || ((ServletServerHttpRequest)request).getServletRequest().getRequestURI().lastIndexOf("swagger-resources")>=0;
		isSwaggerReqest  = isSwaggerReqest || ((ServletServerHttpRequest)request).getServletRequest().getRequestURI().lastIndexOf("v2/api-docs")>=0;
		logger.trace(format("{}", "Response Advice Request URI"),((ServletServerHttpRequest)request).getServletRequest().getRequestURI());

		if (isSwaggerReqest) {
			logger.trace(format("{}", "exclude Wrapping Message VO"),body);
			return body;
		} else { // swagger 의 호출이 아닌 경우
			int rowCount = 1;
			if (body == null) {
				rowCount = 0;
			} else if (body instanceof Collection) {
				rowCount = ((Collection<?>) body).size();
			} else if (body instanceof Page && null != ((Page<?>) body).getContent()) {
				rowCount = ((Page<?>) body).getContent().size();
			}

			// exception 이 발생한 경우 status code 를 변경한다
			// 이미 MessageVo 로 변경된 경우
			// -- exception 이 발생한 경우 이다.
			// -- controller 에서 이미 MessageVo 로 보낸 경우
			if (body instanceof MessageVo) {
				// 값이 존재 할 경우 response code 변경
				if (null != ((MessageVo) body)
						&& null != ((MessageVo) body).getHttpStatus()) {
					// API 진입인지 브라우저 진입인지 판별이 필요하다
					// UI framework 에서 모든 응답에 대해서 200을 원한다.
					//response.setStatusCode(HttpStatus.valueOf(((MessageVo) body).getHttpStatus()));
					response.setStatusCode(HttpStatus.OK);
				}

				logger.trace(format("{}", "Already Wrapping Message VO"),body);
				return body;
			} else {
				logger.trace(format("{}", "Wrapping Message VO"),body);
				return new MessageVo(HttpServletResponse.SC_OK, rowCount, body);
			}
		}
	}
}
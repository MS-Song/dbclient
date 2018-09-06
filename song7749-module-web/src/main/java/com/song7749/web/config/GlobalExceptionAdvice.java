package com.song7749.web.config;

import static com.song7749.util.LogMessageFormatter.format;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.song7749.common.MessageVo;
import com.song7749.common.exception.AuthorityUserException;
import com.song7749.common.exception.MemberNotFoundException;

/**
 * <pre>
 * Class Name : GlobalExceptionAdvice.java
 * Description : 컨트롤러의 Exception 전역 설정을 처리하는 Advice
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
public class GlobalExceptionAdvice {

	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 404 error handler
	 *
	 * @param e
	 * @return MessageVo
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseBody
	MessageVo handleError404(Exception e) {
		logger.debug(format("{}", "REQUEST EXCEPTION STATUS"), e.getMessage());
		if(logger.isDebugEnabled()){
			e.printStackTrace();
		}
		return new MessageVo(getResponseCode(e), e.getMessage());
	}

	/**
	 * 전역 예외 처리 관리
	 *
	 * @param e
	 * @return MessageVo
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	MessageVo handleError(Exception e) {
		StringBuffer sb = new StringBuffer();
		if (e instanceof BindException) {
			BindException be = (BindException) e;
			if (null != be.getFieldError()) {
				sb.append(be.getFieldError().getField());
				sb.append(" 의 ");
				sb.append(be.getFieldError().getCode());
				sb.append(" 은(는) ");
				sb.append(be.getFieldError().getDefaultMessage());
			} else {
				sb.append(e.getMessage());
			}
		} else {
			sb.append(e.getMessage());
		}
		logger.debug(format("{}", "REQUEST EXCEPTION STATUS"), sb.toString());
		if(logger.isDebugEnabled()){
			e.printStackTrace();
		}
		return new MessageVo(getResponseCode(e), sb.toString());
	}

	/**
	 * 익셉션을 통해 httpStatusCode 를 취득한다.
	 *
	 * @param Exception
	 *            ex
	 * @return int httpStatusCode
	 */
	private int getResponseCode(Exception e) {
		int responseCode = 0;
		// 400
		if (e instanceof BindException || e instanceof HttpMessageNotReadableException
				|| e instanceof MethodArgumentNotValidException || e instanceof MissingServletRequestParameterException
				|| e instanceof MissingServletRequestPartException || e instanceof TypeMismatchException
				|| e instanceof IllegalArgumentException) {
			responseCode = HttpServletResponse.SC_BAD_REQUEST;
		}
		// 404
		else if (e instanceof NoHandlerFoundException) {
			responseCode = HttpServletResponse.SC_NOT_FOUND;
		}
		// 405
		else if (e instanceof HttpRequestMethodNotSupportedException
				|| e instanceof LoginException
				|| e instanceof AuthorityUserException
				|| e instanceof MemberNotFoundException) {
			responseCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}
		// 406
		else if (e instanceof HttpMediaTypeNotAcceptableException) {
			responseCode = HttpServletResponse.SC_NOT_ACCEPTABLE;
		}
		// 415
		else if (e instanceof HttpMediaTypeNotSupportedException) {
			responseCode = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
		}
		// 500
		else if (e instanceof HttpMessageNotWritableException || e instanceof ConversionNotSupportedException) {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		// 그외 모든 에러도 500
		else {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		return responseCode;
	}

}
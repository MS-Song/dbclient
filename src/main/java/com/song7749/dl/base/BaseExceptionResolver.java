package com.song7749.dl.base;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * <pre>
 * Class Name : BaseExceptionResolver.java
 * Description : 컨트롤러를 통해 호출된 모든 기능에서 exception 이 발생할 경우
 * 내용을 변경하여 반환한다.
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 4. 28.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 4. 28.
 */
public class BaseExceptionResolver extends SimpleMappingExceptionResolver {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("#{systemProperties['line.separator']}")
	private String lineSeparator; // 운영체제 별 개행문자

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		// http status 정리
		int responseCode = getResponseCode(ex);

		// 익셉션 메세지 변환
		String message = convertExceptionMessage(request, handler, ex);

		// 로그 기록
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));

		// URL 파라메터 기록
		StringBuffer buffer = new StringBuffer();
		Iterator<?> iterator = request.getParameterMap().entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<?, ?> mapEntry = (Entry<?, ?>) iterator.next();

			buffer.append(mapEntry.getKey()
					+ "="
					+ ToStringBuilder.reflectionToString(mapEntry.getValue(),
							ToStringStyle.SIMPLE_STYLE) + ",");
		}

		String params = buffer.toString();
		String requestUrl = "";
		requestUrl += request.getMethod() + " ";
		requestUrl += request.getRequestURL();
		requestUrl += request.getQueryString() != null ? "?"
				+ request.getQueryString() : "";

		StringBuffer logStr = new StringBuffer();
		logStr.append(this.lineSeparator);
		logStr.append("**************************** Exception Info ****************************");
		logStr.append(this.lineSeparator);
		logStr.append(" URL    		: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" PARAMS 		: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" IP     		: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" REFERER 	: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" DATETIME   	: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" MESSAGE    	: {}");
		logStr.append(this.lineSeparator);
		logStr.append(" STACKTRACE 	: {}");
		logStr.append(this.lineSeparator);
		logStr.append("************************************************************************");

		Object[] logParam = { requestUrl, params, request.getRemoteAddr(),
				request.getHeader("referer"),
				new Timestamp(System.currentTimeMillis()).toString(), message,
				sw.toString() };

		// 500 인 경우에는 error log 를 그외에는 debug 로 쌓는다.
		if (responseCode == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
			logger.error(logStr.toString(), logParam);
		} else {
			logger.debug(logStr.toString(), logParam);
		}
		ResponseResult responseResult = new ResponseResult(responseCode,
				message);

		// html 요청인 경우에는 view 를 error_veiw 로 이동시킨다.
		// TODO 확장명을 찾는 더 좋은 방법이 있으면 변경한다. contentType 이 null 이여서..
		ModelAndView mv = new ModelAndView();
		// html 요청이 아닌 경우에만 처리한다.
		String ext = ".html";
		if (request.getRequestURI().lastIndexOf(ext) == request.getRequestURI()
				.length() - ext.length()) {
			mv.setViewName("/errors/error");
			mv.getModelMap().addAttribute("message", message);
		} else {
			mv.getModelMap().addAttribute(responseResult);
		}
		return mv;
	}

	/**
	 * 오류 메세지를 컨퍼팅 해서 사용자가 인식하기 쉽게 변환하여 반환한다.
	 *
	 * @param request
	 * @param handler
	 * @param ex
	 * @return String exceptionMessage
	 */
	private String convertExceptionMessage(HttpServletRequest request,
			Object handler, Exception ex) {
		StringBuffer sb = new StringBuffer();
		// 400 파라메터 관련 에러 메세지인 경우에 컨버팅
		if (ex instanceof TypeMismatchException) {
			try {
				sb.append(lineSeparator);
				sb.append("파라메터 타입이 일치하지 않습니다. 의심되는 파라메터는 다음과 같습니다.");
				// 리퀘스트 파라메터를 가져와서 직접 파싱해보고 에러 포인트를 찾아낸다.
				for (Object parameterName : request.getParameterMap().keySet()) {
					// 메소드에서 호출된 파라메터를 모두 가져온다.
					for (MethodParameter parameter : ((HandlerMethod) handler)
							.getMethodParameters()) {
						// ReqeustParam 인 경우에만 처리해준다.
						if (parameter
								.hasParameterAnnotation(RequestParam.class)) {
							// 파라메터 명칭이 동일한 경우 형변환을 시도한다.
							if (parameterName.toString().equals(
									parameter.getParameterAnnotation(
											RequestParam.class).value())) {
								// 파싱을 해서 처리하려고 했으나 오버헤드가 너무 크다.
								// exception 에서 전달한 값과 같은 값을 찾아서 값이 여러개이면, 의심된다고
								// 보내고, 하나면 찾아서 보낸다.
								String paramValue = request
										.getParameter(parameterName.toString());
								String ExceptionValue = ((TypeMismatchException) ex)
										.getValue().toString();
								// 파라메터의 값이 존재하고, 익셉션에서 찾은 값이 동일한 경우에만 파라메터에 대한
								// 내용을 보여준다.
								if (paramValue != null
										&& ExceptionValue != null
										&& paramValue.equals(ExceptionValue)) {
									sb.append(lineSeparator);
									sb.append(" [ name : ");
									sb.append(parameterName.toString());
									sb.append(" , type : ");
									sb.append(parameter
											.getNestedParameterType()
											.getSimpleName());
									sb.append(" , value : ");
									sb.append(ExceptionValue);
									sb.append(" ] ");
									sb.append(lineSeparator);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// 파라메터 추출 실패 시 기존 익셉션 값을 전달한다
			}
		} else if(ex instanceof BindException){ // Validate 익셉션 처리
			for(FieldError fe : ((BindException) ex).getFieldErrors()){
				sb.append(fe.getField());
				sb.append("=");
				sb.append(" 의(는)(은) ");
				sb.append(fe.getDefaultMessage());
				sb.append("\n");
			}
		} else { //  그외 모든 오류
			sb.append(ex.getMessage());
		}
		return sb.toString();
	}

	/**
	 * 익셉션을 통해 httpStatusCode 를 취득한다.
	 *
	 * @param Exception
	 *            ex
	 * @return int httpStatusCode
	 */
	private int getResponseCode(Exception ex) {
		int responseCode = 0;
		// 400
		if (ex instanceof BindException
				|| ex instanceof HttpMessageNotReadableException
				|| ex instanceof MethodArgumentNotValidException
				|| ex instanceof MissingServletRequestParameterException
				|| ex instanceof MissingServletRequestPartException
				|| ex instanceof TypeMismatchException) {
			responseCode = HttpServletResponse.SC_BAD_REQUEST;
		}
		// 404 는 이쪽으로 들어오지 않는 것 같다.
		else if (ex instanceof NoSuchRequestHandlingMethodException) {
			responseCode = HttpServletResponse.SC_NOT_FOUND;
		}
		// 405
		else if (ex instanceof HttpRequestMethodNotSupportedException) {
			responseCode = HttpServletResponse.SC_METHOD_NOT_ALLOWED;
		}
		// 406
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			responseCode = HttpServletResponse.SC_NOT_ACCEPTABLE;
		}
		// 415
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			responseCode = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
		}
		// 500
		else if (ex instanceof HttpMessageNotWritableException
				|| ex instanceof ConversionNotSupportedException) {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		// 그외 모든 에러도 500
		else {
			responseCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
		return responseCode;
	}
}
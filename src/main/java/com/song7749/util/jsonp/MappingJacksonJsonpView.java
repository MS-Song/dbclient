package com.song7749.util.jsonp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * <pre>
 * Class Name : MappingJacksonJsonpView.java
 * Description : jsonp 지원을 위해 기존 라이브러리 상속하여 수정 처리
 * json 과 jsonp 둘다 지원 가능
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2017. 2. 13.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2017. 2. 13.
 */
public class MappingJacksonJsonpView extends MappingJacksonJsonView {

	/**
	 * Prepares the view given the specified model, merging it with static
	 * attributes and a RequestContext attribute, if necessary. Delegates to
	 * renderMergedOutputModel for the actual rendering.
	 *
	 * @see #renderMergedOutputModel
	 */
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ext = ".jsonp";
		if (request.getRequestURI().lastIndexOf(ext) == request.getRequestURI()
				.length() - ext.length()) {
			if ("GET".equals(request.getMethod().toUpperCase())) {
				Map<String, String[]> params = request.getParameterMap();

				if (params.containsKey("callback")) {
					response.getOutputStream().write(
							new String(params.get("callback")[0] + "(")
									.getBytes());
					super.render(model, request, response);
					response.getOutputStream().write(
							new String(");").getBytes());
					response.setContentType("application/javascript");
				} else {
					super.render(model, request, response);
				}
			} else {
				super.render(model, request, response);
			}
		} else {
			super.render(model, request, response);
		}
	}
}
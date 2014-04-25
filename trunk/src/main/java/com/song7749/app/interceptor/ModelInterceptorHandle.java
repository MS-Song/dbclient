package com.song7749.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.song7749.dl.base.ResponseResult;

public class ModelInterceptorHandle extends HandlerInterceptorAdapter {

	/**
	 * This implementation always returns <code>true</code>.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	/**
	 *
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// html 요청이 아닌 경우에만 처리한다.
		String[] extList = {".json",".xml"};
		boolean isHtml=true;
		for(String ext:extList){
			if(request.getRequestURI().lastIndexOf(ext)>=0){
				isHtml=false;
				break;
			}
		}
		if (isHtml==false) {
			if (modelAndView != null) {
				// 모델이 비어 있는경우 500이 발생하는 것을 차단하기 위해 error message 로 포장한다.
				if (modelAndView.getModel().isEmpty() == true) {
					ResponseResult responseResult = new ResponseResult(
							HttpServletResponse.SC_NO_CONTENT, "데이터가 없습니다.");
					modelAndView.addObject(responseResult);
				}

				// 이미 WRAPPER 를 쓰지 않은 경우에만..
				if (null == modelAndView.getModel().get("responseResult")) {
					ResponseResult responseResult = new ResponseResult(
							HttpServletResponse.SC_OK);
					// 모델에 반드시 1개의 객체만 넣도록 개발자들과 약속을 한다.
					for (Object key : modelAndView.getModel().keySet()) {
						responseResult.setResult(modelAndView.getModel().get(
								key));
					}
					// 기존 객체를 지우고 포장한 객체를 다시 넣는다.
					modelAndView.getModelMap().clear();
					modelAndView.addObject(responseResult);
				}
			}
		}
	}
}
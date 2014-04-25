
package com.song7749.util;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.song7749.dl.base.ResponseResult;

public class ResponseUtil {

	/**
	 * 결과를 반환하기 위해 사용한다.
	 *
	 * @param model
	 * @param result
	 */
	public static void setReturnModel(ModelMap model, Object result) {
		ResponseResult responseResult = new ResponseResult(HttpServletResponse.SC_OK);
		responseResult.setResult(result);

		model.addAttribute(responseResult);
	}

	/**
	 * CUD 일 경우 영향받은 로우를 리턴하기 위해 사용한다.<br/>
	 * TODO 추후 affectedRows 를 삭제하고 result 로 통일 시킨다.
	 * @param model
	 * @param affectedRows
	 */
	public static void setCUDReturnModel(ModelMap model, int affectedRows) {
		ResponseResult responseResult = new ResponseResult(
				HttpServletResponse.SC_OK, affectedRows);
		// result 에도 같은 값을 추가해준다.
		responseResult.setResult(affectedRows);

		model.clear();
		model.addAttribute(responseResult);
	}
}

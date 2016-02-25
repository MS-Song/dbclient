package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : ViewVO.java
 * Description : View
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 24.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 24.
*/

@ApiModel
public class ViewVO extends AbstractVo{

	private static final long serialVersionUID = 2242799985087441360L;

	@ApiModelProperty
	private String viewName;

	@ApiModelProperty
	private String text;

	public ViewVO() {}

	public ViewVO(String viewName, String text) {
		this.viewName = viewName;
		this.text = text;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
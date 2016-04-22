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
	private String comments;

	@ApiModelProperty
	private String lastUpdateTime;

	@ApiModelProperty
	private String status;

	@ApiModelProperty
	private String text;

	public ViewVO() {}


	public ViewVO(String viewName, String comments, String lastUpdateTime,
			String status) {
		this.viewName = viewName;
		this.comments = comments;
		this.lastUpdateTime = lastUpdateTime;
		this.status = status;
	}

	public ViewVO(String viewName, String text) {
		super();
		this.viewName = viewName;
		this.text = text;
	}


	public String getViewName() {
		return viewName;
	}


	public void setViewName(String viewName) {
		this.viewName = viewName;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}


	public String getLastUpdateTime() {
		return lastUpdateTime;
	}


	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
}
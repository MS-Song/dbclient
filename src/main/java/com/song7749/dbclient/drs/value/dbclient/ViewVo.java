package com.song7749.dbclient.drs.value.dbclient;


import com.song7749.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
public class ViewVo extends AbstractVo{

	private static final long serialVersionUID = 2242799985087441360L;

	@ApiModelProperty
	private Integer seq;

	@ApiModelProperty
	private String name;

	@ApiModelProperty
	private String comments;

	@ApiModelProperty
	private String lastUpdateTime;

	@ApiModelProperty
	private String status;

	@ApiModelProperty
	private String text;

	@ApiModelProperty
	private String editText;

	public ViewVo() {}

	public ViewVo(Integer seq, String name, String comments,
			String lastUpdateTime, String status) {
		this.seq = seq;
		this.name = name;
		this.comments = comments;
		this.lastUpdateTime = lastUpdateTime;
		this.status = status;
	}

	public ViewVo(String text, String editText) {
		super();
		this.text = text;
		this.editText = editText;
	}

	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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

	/**
	 * View 를 Edit 할경우 추가되는 SQL 과 VIEW 내용을 더해서 출력 한다.
	 * @return String
	 */
	public String getEditText() {
		return editText + "\n" +text;
	}

	/**
	 * View 를 edit 할경우 추가되는 SQL 을 넣는다.
	 * @param editText
	 */
	public void setEditText(String editText) {
		this.editText = editText;
	}
}
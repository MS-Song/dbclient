package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;

/**
 * <pre>
 * Class Name : FunctionVO.java
 * Description : function 조회 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 3. 17.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 3. 17.
*/
public class FunctionVO extends AbstractVo {

	private static final long serialVersionUID = -2824130086344501249L;

	private String name;
	private String status;
	private String lastUpdateDate;
	private String text;

	public FunctionVO() {}

	public FunctionVO(String text) {
		this.text = text;
	}

	public FunctionVO(String name, String status, String lastUpdateDate) {
		this.name = name;
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}

package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;

/**
 * <pre>
 * Class Name : ProcedureVO.java
 * Description : 프로시저 VO  객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 3. 11.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 3. 11.
*/
public class ProcedureVO extends AbstractVo{

	private static final long serialVersionUID = 2598841737020289017L;

	private String name;
	private String lastUpdateDate;
	private String text;

	public ProcedureVO() {}

	public ProcedureVO(String text) {
		this.text = text;
	}

	public ProcedureVO(String name, String lastUpdateDate) {
		this.name = name;
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
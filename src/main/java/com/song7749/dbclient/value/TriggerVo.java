package com.song7749.dbclient.value;

import com.song7749.base.AbstractVo;

/**
 * <pre>
 * Class Name : TriggerVO.java
 * Description : Trigger VO 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 5. 2.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 5. 2.
*/

public class TriggerVo extends AbstractVo{

	private static final long serialVersionUID = 5027937226984774899L;

	private Integer seq;
	private String name;
	private String status;
	private String lastUpdateDate;
	private String text;
	private String editText;

	public TriggerVo() {}

	public TriggerVo(Integer seq, String name, String status,
			String lastUpdateDate) {
		this.seq = seq;
		this.name = name;
		this.status = status;
		this.lastUpdateDate = lastUpdateDate;
	}

	public TriggerVo(String text, String editText) {
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

	/**
	 * Trigger 를 Edit 할경우 추가되는 SQL 과 Trigger 내용을 더해서 출력 한다.
	 * @return String
	 */
	public String getEditText() {
		return editText + text;
	}

	/**
	 * Trigger 를 edit 할경우 추가되는 SQL 을 넣는다.
	 * @param editText
	 */
	public void setEditText(String editText) {
		this.editText = editText;
	}
}
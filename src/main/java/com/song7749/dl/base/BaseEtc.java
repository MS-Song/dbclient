package com.song7749.dl.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * <pre>
 * Class Name : BaseEtc.java
 * Description : result wrapper
 * controller 를 통해 반환되는 모든 데이터 중에 CUD 의 경우 baseEtc 객체에 포함되어 반환된다.
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
@XStreamAlias("baseEtc")
public class BaseEtc extends BaseObject {
	private static final long serialVersionUID = 759048288556280799L;

	private int affectedRows;

	/**
	 * @return the affectedRows
	 */
	public int getAffectedRows() {
		return affectedRows;
	}

	/**
	 * @param affectedRows
	 *            the affectedRows to set
	 */
	public void setAffectedRows(int affectedRows) {
		this.affectedRows = affectedRows;
	}
}

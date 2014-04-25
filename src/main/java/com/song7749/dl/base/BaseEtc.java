package com.song7749.dl.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("baseEtc")
public class BaseEtc extends BaseObject{
	private static final long serialVersionUID = 759048288556280799L;

	private int affectedRows;

	/**
	 * @return the affectedRows
	 */
	public int getAffectedRows() {
		return affectedRows;
	}

	/**
	 * @param affectedRows the affectedRows to set
	 */
	public void setAffectedRows(int affectedRows) {
		this.affectedRows = affectedRows;
	}
}

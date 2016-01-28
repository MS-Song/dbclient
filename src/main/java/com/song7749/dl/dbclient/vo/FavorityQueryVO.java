package com.song7749.dl.dbclient.vo;

import java.util.Date;

import com.song7749.dl.base.AbstractVo;

public class FavorityQueryVO extends AbstractVo{

	private static final long serialVersionUID = 5335899212115043768L;

	private Integer favorityQuerySeq;
	private String id;
	private String memo;
	private String query;
	private Date inputDate;

	public FavorityQueryVO() {}

	public FavorityQueryVO(Integer favorityQuerySeq, String id, String memo,
			String query, Date inputDate) {
		this.favorityQuerySeq = favorityQuerySeq;
		this.id = id;
		this.memo = memo;
		this.query = query;
		this.inputDate = inputDate;
	}

	public Integer getFavorityQuerySeq() {
		return favorityQuerySeq;
	}

	public void setFavorityQuerySeq(Integer favorityQuerySeq) {
		this.favorityQuerySeq = favorityQuerySeq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
}

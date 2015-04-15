package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;

public class TableVO extends AbstractVo{

	private static final long serialVersionUID = -3104451423491045532L;

	private String tableName;
	private String tableComment;

	public TableVO() {}

	public TableVO(String tableName, String tableComment) {
		this.tableName = tableName;
		this.tableComment = tableComment;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableComment() {
		return tableComment;
	}
	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
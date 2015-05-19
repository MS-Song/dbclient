package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
public class TableVO extends AbstractVo{

	private static final long serialVersionUID = -3104451423491045532L;

	@ApiModelProperty
	private String tableName;
	@ApiModelProperty
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
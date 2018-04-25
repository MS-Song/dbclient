package com.song7749.dbclient.value;


import com.song7749.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TableVo extends AbstractVo{

	private static final long serialVersionUID = -3104451423491045532L;

	@ApiModelProperty
	private Integer seq;
	@ApiModelProperty
	private String tableName;
	@ApiModelProperty
	private String tableComment;

	public TableVo() {}

	public TableVo(String tableName, String tableComment) {
		this.tableName = tableName;
		this.tableComment = tableComment;
	}

	public TableVo(Integer seq, String tableName, String tableComment) {
		this.seq = seq;
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

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
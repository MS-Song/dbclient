package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;


public class FieldVO extends AbstractVo{

	private static final long serialVersionUID = -6548195910234084209L;

	private String columnId;
	private String columnName;
	private String nullable;
	private String columnKey;
	private String dataType;
	private String dataLegnth;
	private String characterset;
	private String extra;
	private String defaultValue;
	private String comment;

	public FieldVO() {}

	public FieldVO(String columnId, String columnName, String nullable,
			String columnKey, String dataType, String dataLegnth,
			String characterset, String extra, String defaultValue,
			String comment) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.nullable = nullable;
		this.columnKey = columnKey;
		this.dataType = dataType;
		this.dataLegnth = dataLegnth;
		this.characterset = characterset;
		this.extra = extra;
		this.defaultValue = defaultValue;
		this.comment = comment;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLegnth() {
		return dataLegnth;
	}

	public void setDataLegnth(String dataLegnth) {
		this.dataLegnth = dataLegnth;
	}

	public String getCharacterset() {
		return characterset;
	}

	public void setCharacterset(String characterset) {
		this.characterset = characterset;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
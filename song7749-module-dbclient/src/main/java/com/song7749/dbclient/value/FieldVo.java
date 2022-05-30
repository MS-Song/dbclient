package com.song7749.dbclient.value;


import com.song7749.common.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel
public class FieldVo extends AbstractVo{

	private static final long serialVersionUID = -6548195910234084209L;

	@ApiModelProperty
	private String tableName;
	@ApiModelProperty
	private String columnId;
	@ApiModelProperty
	private String columnName;
	@ApiModelProperty
	private String nullable;
	@ApiModelProperty
	private String columnKey;
	@ApiModelProperty
	private String dataType;
	@ApiModelProperty
	private String dataLegnth;
	@ApiModelProperty
	private String characterset;
	@ApiModelProperty
	private String extra;
	@ApiModelProperty
	private String defaultValue;
	@ApiModelProperty
	private String comment;

	/**
	 * 기본 생성자
	 */
	public FieldVo() {}

	/**
	 * 테이블 필드 정보 생성자
	 * @param tableName
	 * @param columnName
	 * @param comment
	 */
	public FieldVo(String tableName, String columnName, String comment) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.comment = comment;
	}

	/**
	 * 전체 생성자
	 * @param tableName
	 * @param columnId
	 * @param columnName
	 * @param nullable
	 * @param columnKey
	 * @param dataType
	 * @param dataLegnth
	 * @param characterset
	 * @param extra
	 * @param defaultValue
	 * @param comment
	 */
	public FieldVo(String tableName, String columnId, String columnName,
			String nullable, String columnKey, String dataType,
			String dataLegnth, String characterset, String extra,
			String defaultValue, String comment) {
		this.tableName = tableName;
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

	public String getTableName() {
		return tableName == null ? "" : tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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
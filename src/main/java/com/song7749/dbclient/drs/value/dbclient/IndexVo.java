package com.song7749.dbclient.drs.value.dbclient;


import com.song7749.base.AbstractVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class IndexVo extends AbstractVo{

	private static final long serialVersionUID = -4278215059798990829L;

	@ApiModelProperty
	private String owner;
	@ApiModelProperty
	private String indexName;
	@ApiModelProperty
	private String indexType;
	@ApiModelProperty
	private String columnName;
	@ApiModelProperty
	private String columnPosition;
	@ApiModelProperty
	private String cardinality;
	@ApiModelProperty
	private String unique;
	@ApiModelProperty
	private String descend;

	public IndexVo() {}

	public IndexVo(String owner, String indexName, String indexType,
			String columnName, String columnPosition, String cardinality,
			String unique, String descend) {
		this.owner = owner;
		this.indexName = indexName;
		this.indexType = indexType;
		this.columnName = columnName;
		this.columnPosition = columnPosition;
		this.cardinality = cardinality;
		this.unique = unique;
		this.descend = descend;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnPosition() {
		return columnPosition;
	}

	public void setColumnPosition(String columnPosition) {
		this.columnPosition = columnPosition;
	}

	public String getCardinality() {
		return cardinality;
	}

	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getDescend() {
		return descend;
	}

	public void setDescend(String descend) {
		this.descend = descend;
	}
}
package com.song7749.dl.dbclient.vo;

import com.song7749.dl.base.AbstractVo;

public class IndexVO extends AbstractVo{

	private static final long serialVersionUID = -4278215059798990829L;

	private String owner;
	private String indexName;
	private String indexType;
	private String columnName;
	private String columnPosition;
	private String cardinality;
	private String unique;
	private String descend;

	public IndexVO() {}

	public IndexVO(String owner, String indexName, String indexType,
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
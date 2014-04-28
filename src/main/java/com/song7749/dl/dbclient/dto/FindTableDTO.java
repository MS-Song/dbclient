package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Dto;
import com.song7749.util.validate.ValidateGroupSelect;

public class FindTableDTO extends Dto{

	private static final long serialVersionUID = 3720276959059559353L;

	@NotNull(groups={ValidateGroupSelect.class})
	private Integer serverInfoSeq;

	@NotNull
	private String tableName;

	public FindTableDTO() {}

	public FindTableDTO(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public FindTableDTO(Integer serverInfoSeq, String tableName) {
		this.serverInfoSeq = serverInfoSeq;
		this.tableName = tableName;
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


}
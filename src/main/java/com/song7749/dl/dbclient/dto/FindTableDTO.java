package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;
import com.song7749.util.validate.ValidateGroupSelect;

public class FindTableDTO extends AbstractDto{

	private static final long serialVersionUID = 3720276959059559353L;

	@NotNull(groups={ValidateGroupSelect.class})
	private Integer serverInfoSeq;

	@NotNull
	private String tableName;

	public FindTableDTO() {}

	public FindTableDTO(boolean useCache) {
		super.setUseCache(useCache);
	}

	public FindTableDTO(Integer serverInfoSeq,boolean useCache) {
		this.serverInfoSeq = serverInfoSeq;
		super.setUseCache(useCache);
	}

	public FindTableDTO(Integer serverInfoSeq, String tableName,boolean useCache) {
		this.serverInfoSeq = serverInfoSeq;
		this.tableName = tableName;
		super.setUseCache(useCache);
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
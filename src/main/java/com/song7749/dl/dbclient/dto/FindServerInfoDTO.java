package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;

public class FindServerInfoDTO extends AbstractDto{

	private static final long serialVersionUID = 8442142510022994438L;

	@NotNull
	private Integer serverInfoSeq;

	public FindServerInfoDTO() {}

	public FindServerInfoDTO(boolean useCache) {
		super.setUseCache(useCache);
	}


	public FindServerInfoDTO(Integer serverInfoSeq, boolean useCache) {
		this.serverInfoSeq = serverInfoSeq;
		super.setUseCache(useCache);
	}


	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}


	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

}
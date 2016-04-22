package com.song7749.dl.dbclient.dto;

import com.song7749.dl.base.AbstractDto;

public class FindTableDTO extends AbstractDto{

	private static final long serialVersionUID = 3720276959059559353L;

	private Integer serverInfoSeq;

	/**
	 * object name
	 */
	private String name;

	public FindTableDTO() {}

	public FindTableDTO(boolean useCache) {
		super.setUseCache(useCache);
	}

	public FindTableDTO(Integer serverInfoSeq, boolean useCache) {
		this.serverInfoSeq = serverInfoSeq;
		super.setUseCache(useCache);
	}

	public FindTableDTO(Integer serverInfoSeq, String name) {
		this.serverInfoSeq = serverInfoSeq;
		this.name = name;
	}

	public FindTableDTO(Integer serverInfoSeq, String name, boolean useCache) {
		this.serverInfoSeq = serverInfoSeq;
		this.name = name;
		super.setUseCache(useCache);
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
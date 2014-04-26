package com.song7749.dl.dbclient.dto;

import com.song7749.dl.base.Dto;

public class FindServerInfoListDTO extends Dto{

	private static final long serialVersionUID = -2933768698426325461L;

	private String host;

	public FindServerInfoListDTO() {}

	public FindServerInfoListDTO(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}

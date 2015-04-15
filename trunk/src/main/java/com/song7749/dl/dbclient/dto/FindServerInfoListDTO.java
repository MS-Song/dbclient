package com.song7749.dl.dbclient.dto;

import com.song7749.dl.base.AbstractDto;

public class FindServerInfoListDTO extends AbstractDto{

	private static final long serialVersionUID = -2933768698426325461L;

	private String host;

	private String schemaName;

	private String account;

	public FindServerInfoListDTO() {}

	public FindServerInfoListDTO(String host) {
		this.host = host;
	}

	public FindServerInfoListDTO(String host, String schemaName, String account) {
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
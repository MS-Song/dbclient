package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;


public class ExecuteResultListDTO extends AbstractDto{

	private static final long serialVersionUID = 7378998437614912567L;

	@NotNull
	private Integer serverInfoSeq;
	@NotNull
	private String host;
	@NotNull
	private String schemaName;
	@NotNull
	private String account;
	@NotNull
	private boolean autoCommit;
	@NotNull
	private String query;
	@NotNull
	private boolean htmlAllow;
	@NotNull
	private String id;
	@NotNull
	private String ip;


	public ExecuteResultListDTO() {}

	public ExecuteResultListDTO(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public ExecuteResultListDTO(String query) {
		this.query = query;
	}

	public ExecuteResultListDTO(Integer serverInfoSeq, String host,
			String schemaName, String account, boolean autoCommit,
			String query, boolean htmlAllow, String id, String ip) {
		this.serverInfoSeq = serverInfoSeq;
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.autoCommit = autoCommit;
		this.query = query;
		this.htmlAllow = htmlAllow;
		this.id = id;
		this.ip = ip;
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
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

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public boolean isHtmlAllow() {
		return htmlAllow;
	}

	public void setHtmlAllow(boolean htmlAllow) {
		this.htmlAllow = htmlAllow;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
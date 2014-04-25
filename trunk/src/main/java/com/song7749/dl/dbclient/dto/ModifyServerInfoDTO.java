package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Dto;

public class ModifyServerInfoDTO extends Dto{

	private static final long serialVersionUID = -3133476417878842598L;

	@NotNull
	private Integer serverInfoSeq;

	private String host;

	private String schemaName;

	private String account;

	private String password;

	private String dirver;

	private String charset;

	private String port;

	public ModifyServerInfoDTO() {}

	public ModifyServerInfoDTO(Integer serverInfoSeq, String host,
			String schemaName, String account, String password, String dirver,
			String charset,String port) {
		this.serverInfoSeq = serverInfoSeq;
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.dirver = dirver;
		this.charset = charset;
		this.port = port;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDirver() {
		return dirver;
	}
	public void setDirver(String dirver) {
		this.dirver = dirver;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
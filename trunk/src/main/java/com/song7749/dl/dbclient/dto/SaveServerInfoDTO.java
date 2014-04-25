package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Dto;

public class SaveServerInfoDTO extends Dto{

	private static final long serialVersionUID = 1403398882918633647L;

	@NotNull
	private String host;
	@NotNull
	private String schemaName;
	@NotNull
	private String account;
	@NotNull
	private String password;
	@NotNull
	private String dirver;
	@NotNull
	private String charset;
	@NotNull
	private String port;

	public SaveServerInfoDTO(){}

	public SaveServerInfoDTO(String host, String schemaName, String account,
			String password, String dirver, String charset,String port) {
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.dirver = dirver;
		this.charset = charset;
		this.port = port;
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
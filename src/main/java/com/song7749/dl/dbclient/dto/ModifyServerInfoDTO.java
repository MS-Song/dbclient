package com.song7749.dl.dbclient.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;
import com.song7749.dl.dbclient.type.DatabaseDriver;

public class ModifyServerInfoDTO extends AbstractDto{

	private static final long serialVersionUID = -3133476417878842598L;

	@NotNull
	private Integer serverInfoSeq;

	private String host;

	private String hostAlias;

	private String schemaName;

	private String account;

	private String password;

	private DatabaseDriver driver;

	private String charset;

	private String port;

	public ModifyServerInfoDTO() {}

	public ModifyServerInfoDTO(Integer serverInfoSeq, String hostAlias, String host,
			String schemaName, String account, String password, DatabaseDriver driver,
			String charset,String port) {
		this.serverInfoSeq = serverInfoSeq;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
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

	public String getHostAlias() {
		return hostAlias;
	}

	public void setHostAlias(String hostAlias) {
		this.hostAlias = hostAlias;
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
	public DatabaseDriver getDriver() {
		return driver;
	}
	public void setDriver(DatabaseDriver driver) {
		this.driver = driver;
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
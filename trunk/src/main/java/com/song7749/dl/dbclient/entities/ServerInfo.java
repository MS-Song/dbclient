package com.song7749.dl.dbclient.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Entities;
import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupUpdate;

@Entity
@Table
public class ServerInfo extends Entities {

	private static final long serialVersionUID = 1522920445714322487L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull(groups={ValidateGroupUpdate.class,ValidateGroupDelete.class})
	private Integer serverInfoSeq;

	@Column
	@NotNull
	private String host;

	@Column
	@NotNull
	private String schemaName;

	@Column
	@NotNull
	private String account;

	@Column
	@NotNull
	private String password;

	@Column
	@NotNull
	private String dirver;

	@Column
	@NotNull
	private String charset;

	@Column
	@NotNull
	private String port;

	public ServerInfo() {}

	public ServerInfo(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public ServerInfo(String host, String schemaName,
			String account, String password, String dirver, String charset,String port) {
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.dirver = dirver;
		this.charset = charset;
		this.port = port;
	}

	public ServerInfo(Integer serverInfoSeq, String host, String schemaName,
			String account, String password, String dirver, String charset,String port) {
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
		return this.serverInfoSeq;
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
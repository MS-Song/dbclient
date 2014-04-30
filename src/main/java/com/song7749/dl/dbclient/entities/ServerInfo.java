package com.song7749.dl.dbclient.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Entities;
import com.song7749.dl.dbclient.type.DatabaseDriver;
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
	@Enumerated(EnumType.STRING)
	@NotNull
	private DatabaseDriver driver;

	@Column
	@NotNull
	private String charset;

	@Column
	@NotNull
	private String port;

	@Transient
	private String tableName;

	public ServerInfo() {}

	public ServerInfo(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public ServerInfo(Integer serverInfoSeq , String tableName) {
		this.serverInfoSeq = serverInfoSeq;
		this.tableName = tableName;
	}

	public ServerInfo(String host, String schemaName,
			String account, String password, DatabaseDriver driver, String charset,String port) {
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
	}

	public ServerInfo(Integer serverInfoSeq, String host, String schemaName,
			String account, String password, DatabaseDriver driver, String charset,String port) {
		this.serverInfoSeq = serverInfoSeq;
		this.host = host;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
package com.song7749.dbclient.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.song7749.common.base.AbstractDto;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

import io.swagger.annotations.ApiModel;

@ApiModel("데이터베이스 정보 수정 객체")
public class DatabaseModifyDto  extends AbstractDto {

	private static final long serialVersionUID = -6233451074229529873L;

	@NotNull
	@Min(1L)
	private Long id;

	private String host;

	private String hostAlias;

	private String schemaName;

	private String schemaOwner;

	private String account;

	private String password;

	@Enumerated(EnumType.STRING)
	private DatabaseDriver driver;

	@Enumerated(EnumType.STRING)
	private Charset charset;

	private String port;

	public DatabaseModifyDto() {}


	/**
	 * @param id
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 */
	public DatabaseModifyDto(
			 Long id,
			 String host,
			 String hostAlias,
			 String schemaName,
			 String account,
			 String password,
			 DatabaseDriver driver,
			 Charset charset,
			 String port) {
		super();
		this.id = id;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	public String getSchemaOwner() {
		return schemaOwner;
	}


	public void setSchemaOwner(String schemaOwner) {
		this.schemaOwner = schemaOwner;
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


	public Charset getCharset() {
		return charset;
	}


	public void setCharset(Charset charset) {
		this.charset = charset;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}
}
package com.song7749.dbclient.drs.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.song7749.base.AbstractDto;
import com.song7749.dbclient.drs.type.Charset;
import com.song7749.dbclient.drs.type.DatabaseDriver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("데이터베이스 입력 모델")
public class DatabaseAddDto  extends AbstractDto {

	private static final long serialVersionUID = 2469669840827588753L;

	@Length(max=120)
	@NotBlank
	@ApiParam(value="Database Host Name (IP Adress OR Domain)"
				,example="127.0.0.1 or db.song7749.com"
				,required=true)
	private String host;

	@Length(max=120)
	@NotBlank
	@ApiParam(value="Database Host Alias"
				,example=" XXX Service Test / XXX Service Production ... ENV Config"
				,required=true)
	private String hostAlias;

	@Length(max=120)
	@NotBlank
	@ApiParam(value="Database Schema(Mysql), SID(Oracle)... "
				,required=true)
	private String schemaName;

	@Length(max=60)
	@NotBlank
	@ApiParam(value="Database Account"
				,required=true)
	private String account;

	@Length(min=4,max=20)
	@NotBlank
	@ApiParam(value="Database Password"
				,required=true
				,format="password")
	private String password;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiParam(value="Database Driver Selection"
				,required=true)
	private DatabaseDriver driver;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiParam(value="Database Connnect Charicter Set"
				,required=true)
	private Charset charset;

	@Length(max=5)
	@NotBlank
	@ApiParam(value="Database Connect Port"
				,required=true)
	private String port;

	public DatabaseAddDto() {}


	/**
	 * @param host
	 * @param hostAlias
	 * @param schemaName
	 * @param account
	 * @param password
	 * @param driver
	 * @param charset
	 * @param port
	 */
	public DatabaseAddDto(
			 String host,
			 String hostAlias,
			 String schemaName,
			 String account,
			 String password,
			 DatabaseDriver driver,
			 Charset charset,
			 String port) {
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.password = password;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
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
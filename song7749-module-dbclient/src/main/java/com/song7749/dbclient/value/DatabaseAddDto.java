package com.song7749.dbclient.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.song7749.common.AbstractDto;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("데이터베이스 입력 모델")
public class DatabaseAddDto  extends AbstractDto {

	private static final long serialVersionUID = 2469669840827588753L;

	@Length(max=120)
	@NotBlank
	@ApiModelProperty(value="Database Host Name (IP Adress OR Domain)"
				,example="127.0.0.1 or db.song7749.com, if embaded database (EX h2, sqlite) is file-path"
				,required=true)
	private String host;

	@Length(max=120)
	@NotBlank
	@ApiModelProperty(value="Database Host Alias"
				,example=" XXX Service Test / XXX Service Production ... ENV Config"
				,required=true)
	private String hostAlias;

	@Length(max=120)
	@NotBlank
	@ApiModelProperty(value="Database Schema(Mysql), SID(Oracle)..., if embaded database is skip.")
	private String schemaName;

	@Length(max=120)
	@ApiModelProperty(value="Oracle 접속 계정과 Database Owner 가 다를 경우에 추가 입력")
	private String schemaOwner;

	@Length(max=60)
	@NotBlank
	@ApiModelProperty(value="Database Account" ,required=true)
	private String account;

	@ApiModelProperty(value="Database Password"
			,example="if database password not set, it input valuse is null or empty.")
	private String password;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiModelProperty(value="Database Driver Selection",required=true)
	private DatabaseDriver driver;

	@Enumerated(EnumType.STRING)
	@NotNull
	@ApiModelProperty(value="Database Connnect Charicter Set",required=true)
	private Charset charset;

	@ApiModelProperty(value="Database Connect Port"
			,example="if embaded file database (EX H2, sqlite) is null or empty input.")
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
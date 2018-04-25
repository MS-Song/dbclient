package com.song7749.dbclient.value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.base.AbstractVo;
import com.song7749.dbclient.type.Charset;
import com.song7749.dbclient.type.DatabaseDriver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("데이터베이스 연결 정보")
public class DatabaseVo extends AbstractVo {

	private static final long serialVersionUID = 2469669840827588753L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty("Host Name")
	private String host;

	@ApiModelProperty("Host Alias")
	private String hostAlias;

	@ApiModelProperty("Schema(SID)")
	private String schemaName;

	@ApiModelProperty("Account")
	private String account;

	@ApiModelProperty("Driver")
	private DatabaseDriver driver;

	@ApiModelProperty("Charicter Set")
	private Charset charset;

	@ApiModelProperty("Port")
	private String port;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@ApiModelProperty(value = "Create")
	private Date createDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	@ApiModelProperty(value = "Modify")
	private Date modifyDate;

	public DatabaseVo() {}


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
	 * @param createDate
	 * @param modifyDate
	 */
	public DatabaseVo(Long id, String host, String hostAlias, String schemaName, String account, String password,
			DatabaseDriver driver, Charset charset, String port, Date createDate, Date modifyDate) {
		this.id = id;
		this.host = host;
		this.hostAlias = hostAlias;
		this.schemaName = schemaName;
		this.account = account;
		this.driver = driver;
		this.charset = charset;
		this.port = port;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
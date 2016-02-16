package com.song7749.dl.dbclient.vo;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.song7749.dl.base.AbstractVo;
import com.song7749.dl.dbclient.type.DatabaseDriver;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(description="데이터베이스 서버 정보",subTypes={DatabaseDriver.class})
@JsonPropertyOrder({
	"serverInfoSeq",
	"host",
	"hostAlias",
	"schemaName",
	"account",
	"password",
	"driver",
	"charset",
	"port"})
public class ServerInfoVO extends AbstractVo{

	private static final long serialVersionUID = -8989212849769127761L;

	@ApiModelProperty(hidden=true,position=1)
	private Integer serverInfoSeq;

	@ApiModelProperty(position=2)
	private String host;

	@ApiModelProperty(position=3)
	private String hostAlias;

	@ApiModelProperty(position=4)
	private String schemaName;

	@ApiModelProperty(position=5)
	private String account;

	@ApiModelProperty(position=6)
	private String password;

	@ApiModelProperty(position=7)
	private DatabaseDriver driver;

	@ApiModelProperty(position=8)
	private String charset;

	@ApiModelProperty(position=9)
	private String port;

	public ServerInfoVO(){}

	public ServerInfoVO(Integer serverInfoSeq, String host, String hostAlias, String schemaName,
			String account, String password, DatabaseDriver driver, String charset,String port) {
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
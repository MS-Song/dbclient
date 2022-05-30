package com.song7749.mail.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.song7749.common.base.AbstractVo;
import com.song7749.mail.type.EmailProtocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("메일 환경 설정")
public class MailConfigVo extends AbstractVo{

	private static final long serialVersionUID = -2837827506976032498L;

	@ApiModelProperty(position=1,value="HOST")
	private String host;

	@ApiModelProperty(position=1,value="PORT")
	private Integer port;

	@ApiModelProperty(position=1,value="AUTH")
	private Boolean auth = true;

	@ApiModelProperty(position=1,value="USER NAME")
	private String username;

	@ApiModelProperty(position=1,value="PASSWORD")
	private String password;

	@ApiModelProperty(position=1,value="PROTOCOL")
	@Enumerated(EnumType.STRING)
	private EmailProtocol protocol;

	@ApiModelProperty(position=1,value="ENABLE SSL")
	private Boolean enableSSL = true;

	@ApiModelProperty(position=1,value="START TLS")
	private Boolean starttls = true;

	public MailConfigVo() {}

	/**
	 * @param host
	 * @param port
	 * @param auth
	 * @param username
	 * @param password
	 * @param protocol
	 * @param enableSSL
	 * @param starttls
	 */
	public MailConfigVo(String host, Integer port, Boolean auth, String username, String password,
			EmailProtocol protocol, Boolean enableSSL, Boolean starttls) {
		super();
		this.host = host;
		this.port = port;
		this.auth = auth;
		this.username = username;
		this.password = password;
		this.protocol = protocol;
		this.enableSSL = enableSSL;
		this.starttls = starttls;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmailProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(EmailProtocol protocol) {
		this.protocol = protocol;
	}

	public Boolean getEnableSSL() {
		return enableSSL;
	}

	public void setEnableSSL(Boolean enableSSL) {
		this.enableSSL = enableSSL;
	}

	public Boolean getStarttls() {
		return starttls;
	}

	public void setStarttls(Boolean starttls) {
		this.starttls = starttls;
	}
}
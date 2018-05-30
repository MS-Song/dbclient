package com.song7749.common.value;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.song7749.base.AbstractDto;
import com.song7749.mail.type.EmailProtocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("email server 환경 설정")
public class MailConfigDto extends AbstractDto{

	private static final long serialVersionUID = -1609376767301971918L;

	@ApiModelProperty(position=1,value="HOST || EX) mail.google.co.kr")
	@NotBlank
	private String host;

	@ApiModelProperty(position=1,value="PORT || EX) NORMAL:25 / SSL:465 / TLS:587")
	@NotNull
	private Integer port;

	@ApiModelProperty(position=1,value="AUTH || 메일 전송시 사용자 인증 필요 여부")
	private Boolean auth = true;

	@ApiModelProperty(position=1,value="USER NAME || 인증이 필요할 경우 사용자 ID EX) 지메일 주소")
	private String username;

	@ApiModelProperty(position=1,value="PASSWORD || 인증이 필요할 경우 사용자 패스워드")
	private String password;

	@ApiModelProperty(position=1,value="PROTOCOL || 메일 프로토콜 선택")
	@NotNull
	@Enumerated(EnumType.STRING)
	private EmailProtocol protocol = EmailProtocol.smtp;

	@ApiModelProperty(position=1,value="ENABLE SSL || SSL 통신 : 포트 번호도 변경해야 함.")
	private Boolean enableSSL = true;

	@ApiModelProperty(position=1,value="START TLS || TLS 통신 : 포트 번호도 변경해야 함.")
	private Boolean starttls = true;

	public MailConfigDto() {}

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
	public MailConfigDto(@NotBlank String host, @NotNull Integer port, Boolean auth, String username, String password,
			@NotNull EmailProtocol protocol, Boolean enableSSL, Boolean starttls) {
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
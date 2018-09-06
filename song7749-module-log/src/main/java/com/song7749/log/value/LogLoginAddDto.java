package com.song7749.log.value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.modelmapper.ModelMapper;

import com.song7749.common.AbstractDto;
import com.song7749.log.domain.LogLogin;

public class LogLoginAddDto extends AbstractDto {

	private static final long serialVersionUID = 1207184553943521808L;

	@NotBlank
	@Size(min = 8, max = 64)
	private String ip;

	@Email
	@NotBlank
	private String loginId;

	@NotBlank
	private String cipher;

	public LogLoginAddDto() {}

	/**
	 * @param ip
	 * @param loginId
	 * @param cipher
	 */
	public LogLoginAddDto(@NotBlank @Size(min = 8, max = 64) String ip,
			@Email @NotBlank String loginId, @NotBlank String cipher) {
		this.ip = ip;
		this.loginId = loginId;
		this.cipher = cipher;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public LogLogin getLogLogin(ModelMapper mapper) {
		return mapper.map(this, LogLogin.class);
	}
}

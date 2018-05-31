package com.song7749.log.value;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractVo;

import io.swagger.annotations.ApiModel;


@ApiModel("로그인 인증 정보")
public class LogLoginVo extends AbstractVo {

	private static final long serialVersionUID = 8796038916710177418L;

	private Long id;

	private String ip;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	private String loginId;

	private String cipher;

	public LogLoginVo() {}

	/**
	 * @param id
	 * @param ip
	 * @param date
	 * @param loginId
	 * @param cipher
	 */
	public LogLoginVo(Long id, String ip, Date date, String loginId, String cipher) {
		this.id = id;
		this.ip = ip;
		this.date = date;
		this.loginId = loginId;
		this.cipher = cipher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
}
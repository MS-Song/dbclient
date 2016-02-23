package com.song7749.log.dto;

import java.util.Date;

import com.song7749.dl.base.AbstractDto;

/**
 * <pre>
 * Class Name : SaveMemberLoginLogDTO.java
 * Description : 회원 로그인 로그 조회 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 23.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 23.
*/
public class SaveMemberLoginLogDTO extends AbstractDto{

	private static final long serialVersionUID = -4528875891729785224L;

	private String id;

	private String ip;

	private String cipher;

	private Date loginDate;


	public SaveMemberLoginLogDTO() {}

	public SaveMemberLoginLogDTO(String id, String ip, String cipher,
			Date loginDate) {
		this.id = id;
		this.ip = ip;
		this.cipher = cipher;
		this.loginDate = loginDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCipher() {
		return cipher;
	}

	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
}
package com.song7749.log.vo;

import java.util.Date;

import com.song7749.dl.base.AbstractVo;

/**
 * <pre>
 * Class Name : MemberLoginLogVO.java
 * Description : 회원 로그인 로그 VO 객체
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
public class MemberLoginLogVO extends AbstractVo{

	private static final long serialVersionUID = -3649240607070013545L;

	private Integer memberLoginLogSeq;

	private String id;

	private String ip;

	private String cipher;

	private Date loginDate;

	public MemberLoginLogVO() {}

	public MemberLoginLogVO(Integer memberLoginLogSeq, String id, String ip,
			String cipher, Date loginDate) {
		this.memberLoginLogSeq = memberLoginLogSeq;
		this.id = id;
		this.ip = ip;
		this.cipher = cipher;
		this.loginDate = loginDate;
	}

	public Integer getMemberLoginLogSeq() {
		return memberLoginLogSeq;
	}

	public void setMemberLoginLogSeq(Integer memberLoginLogSeq) {
		this.memberLoginLogSeq = memberLoginLogSeq;
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

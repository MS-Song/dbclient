package com.song7749.log.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.Entities;
import com.song7749.util.validate.ValidateGroupInsert;

/**
 * <pre>
 * Class Name : MemberLoginLog.java
 * Description : 회원 로그인 로그
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 2. 22.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 2. 22.
*/

@Entity
public class MemberLoginLog extends Entities{

	private static final long serialVersionUID = -3112045629004119981L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer memberLoginLogSeq;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	@Size(min=4,max=20)
	private String id;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String ip;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private String cipher;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	private Date loginDate;

	public MemberLoginLog(String id, String ip,
			String cipher, Date loginDate) {
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
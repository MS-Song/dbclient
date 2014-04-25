package com.song7749.dl.member.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.song7749.dl.base.Entities;

/**
 * <pre>
 * Class Name : AuthList.java
 * Description : 권한 리스트
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 22.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 22.
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "authCode",name="UK_authCode")})
public class AuthList extends Entities {

	private static final long serialVersionUID = 2177336930086021873L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer authListSeq;

	@Column
	private Integer authCode;

	@Column
	private String authName;


	public AuthList(Integer authListSeq) {
		this.authListSeq = authListSeq;
	}

	public AuthList(Integer authCode, String authName) {
		this.authCode = authCode;
		this.authName = authName;
	}

	public AuthList(Integer authListSeq, Integer authCode, String authName) {
		this.authListSeq = authListSeq;
		this.authCode = authCode;
		this.authName = authName;
	}

	public Integer getAuthListSeq() {
		return authListSeq;
	}

	public void setAuthListSeq(Integer authListSeq) {
		this.authListSeq = authListSeq;
	}

	public Integer getAuthCode() {
		return authCode;
	}

	public void setAuthCode(Integer authCode) {
		this.authCode = authCode;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}
}
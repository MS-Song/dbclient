package com.song7749.dl.member.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;

import com.song7749.dl.base.Entities;

/**
 * <pre>
 * Class Name : MemberAuth.java
 * Description : 회원 권한 정보
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 * -----------------------------------------------
 *  2014. 4. 21.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2014. 4. 21.
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id","authCode"},name="UK_id_authCode")})
public class MemberAuth extends Entities{

	private static final long serialVersionUID = 3575039257379611826L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer memberAuthSeq;

	@Column
	private Integer authCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="FK_Member_MemberAuth")
	@JoinColumn(name = "id", nullable = false, insertable = true, updatable = false)
	private Member member;

	public MemberAuth(){}

	public MemberAuth(Integer authCode) {
		this.authCode = authCode;
	}

	public MemberAuth(Integer memberAuthSeq, Integer authCode, Member member) {
		this.memberAuthSeq = memberAuthSeq;
		this.authCode = authCode;
		this.member = member;
	}

	public Integer getMemberAuthSeq() {
		return memberAuthSeq;
	}

	public void setMemberAuthSeq(Integer memberAuthSeq) {
		this.memberAuthSeq = memberAuthSeq;
	}

	public Integer getAuthCode() {
		return authCode;
	}

	public void setAuthCode(Integer authCode) {
		this.authCode = authCode;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
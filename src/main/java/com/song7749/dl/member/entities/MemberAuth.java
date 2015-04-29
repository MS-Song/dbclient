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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import com.song7749.dl.base.Entities;
import com.song7749.dl.member.type.AuthType;
import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupUpdate;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id","authType"},name="UK_id_authType")})
public class MemberAuth extends Entities{

	private static final long serialVersionUID = 3575039257379611826L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull(groups={ValidateGroupUpdate.class,
			ValidateGroupDelete.class})
	private Integer memberAuthSeq;

	@Column
	@NotNull(groups={ValidateGroupInsert.class
			,ValidateGroupUpdate.class})
	private AuthType authType;

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name="FK_Member_MemberAuth")
	@JoinColumn(name = "id", nullable = false, insertable = true, updatable = false)
	private Member member;

	public MemberAuth(){}

	/**
	 * 입력 생성자
	 * @param authType
	 * @param member
	 */
	public MemberAuth(AuthType authType) {
		super();
		this.authType = authType;
	}

	/**
	 * 전체 생성자
	 * @param memberAuthSeq
	 * @param authType
	 * @param member
	 */
	public MemberAuth(Integer memberAuthSeq, AuthType authType) {
		super();
		this.memberAuthSeq = memberAuthSeq;
		this.authType = authType;
	}

	public Integer getMemberAuthSeq() {
		return memberAuthSeq;
	}

	public void setMemberAuthSeq(Integer memberAuthSeq) {
		this.memberAuthSeq = memberAuthSeq;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
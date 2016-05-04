package com.song7749.dl.member.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.song7749.dl.base.Entities;
import com.song7749.util.validate.ValidateGroupDelete;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupUpdate;

/**
 * <pre>
 * Class Name : MemberDatabase.java
 * Description : 회원과 데이터베이스 간의 연결 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2016. 5. 2.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2016. 5. 2.
*/

@Entity
public class MemberDatabase extends Entities{

	private static final long serialVersionUID = -1041299338348630263L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull(groups={ValidateGroupUpdate.class,ValidateGroupDelete.class})
	private Integer memberDatabaseSeq;

	@NotNull(groups={ValidateGroupInsert.class,ValidateGroupUpdate.class})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", nullable = false, insertable = true, updatable = false)
	private Member member;

	@Column
	private Integer serverInfoSeq;

	public MemberDatabase() {}

	public MemberDatabase(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}

	public Integer getMemberDatabaseSeq() {
		return memberDatabaseSeq;
	}

	public void setMemberDatabaseSeq(Integer memberDatabaseSeq) {
		this.memberDatabaseSeq = memberDatabaseSeq;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getServerInfoSeq() {
		return serverInfoSeq;
	}

	public void setServerInfoSeq(Integer serverInfoSeq) {
		this.serverInfoSeq = serverInfoSeq;
	}
}
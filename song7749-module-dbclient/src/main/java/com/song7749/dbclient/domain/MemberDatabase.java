package com.song7749.dbclient.domain;

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

import org.modelmapper.ModelMapper;

import com.song7749.common.Entities;
import com.song7749.dbclient.value.MemberDatabaseVo;
import com.song7749.member.domain.Member;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"database_id","member_id"}, name = "UK_MEMBER_ID_DATABASE_ID") })
public class MemberDatabase extends Entities{

	private static final long serialVersionUID = 7810908005225133156L;

	@Id
	@Column(name="member_databse_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(targetEntity=Database.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "database_id", nullable = false, insertable = true, updatable = false)
	private Database database;

	@NotNull
	@ManyToOne(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, insertable = true, updatable = false)
	private Member member;


	public MemberDatabase() {}

	/**
	 * @param member
	 */
	public MemberDatabase(@NotNull Member member) {
		this.member = member;
	}

	/**
	 * @param database
	 */
	public MemberDatabase(@NotNull Database database) {
		this.database = database;
	}

	/**
	 * @param database
	 * @param member
	 */
	public MemberDatabase(@NotNull Database database, @NotNull Member member) {
		this.database = database;
		this.member = member;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Database getDatabase() {
		return database;
	}


	public void setDatabase(Database database) {
		this.database = database;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}

	public MemberDatabaseVo getMemberDatabaseVo(ModelMapper mapper) {
		return mapper.map(this, MemberDatabaseVo.class);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDatabase other = (MemberDatabase) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
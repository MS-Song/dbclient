package com.song7749.dbclient.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.Entities;
import com.song7749.dbclient.value.MemberSaveQueryVo;

/**
 * <pre>
 * Class Name : MemberSaveQuery.java
 * Description : Database 에서 실행 가능한 Query 를 저장한다.
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 22.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 2. 22.
 */

@Entity
@DynamicUpdate(true)
public class MemberSaveQuery extends Entities {

	private static final long serialVersionUID = -1967307874120580603L;

	@Id
	@Column(name = "member_save_query_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@NotBlank
	@Size(min = 4)
	private String memo;

	@Column
	@NotBlank
	@Size(min = 10, max=8000)
	private String query;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date createDate;

	@Column(nullable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date modifyDate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, insertable = true, updatable = false)
	private Member member;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "database_id", nullable = false, insertable = true, updatable = false)
	private Database database;

	/**
	 *
	 */
	public MemberSaveQuery() {
	}

	/**
	 * @param memo
	 * @param query
	 * @param member
	 * @param database
	 */
	public MemberSaveQuery(@NotBlank @Size(min = 4) String memo, @NotBlank @Size(min = 10) String query,
			@NotNull Member member, @NotNull Database database) {
		this.memo = memo;
		this.query = query;
		this.member = member;
		this.database = database;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public MemberSaveQueryVo getMemberSaveQueryVo(ModelMapper mapper) {
		return mapper.map(this, MemberSaveQueryVo.class);
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
		MemberSaveQuery other = (MemberSaveQuery) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
package com.song7749.dbclient.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.Entities;
import com.song7749.common.YN;
import com.song7749.member.domain.Member;

/**
 * <pre>
 * Class Name : DatabasePrivacyPolicy.java
 * Description : 데이터베이스 개인정보 보호 정책을 위한 데이터
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 6. 4.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 6. 4.
*/

@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"database_id","tableName","fieldName"}, name = "UK_DATABASE_PRIVACY_POLICY") })
public class DatabasePrivacyPolicy extends Entities {

	private static final long serialVersionUID = 1182429146028562053L;

	@Id
	@Column(name="database_privacy_policy_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Length(max = 255)
	@NotBlank
	private String tableName;

	@Column(nullable = false)
	@Length(max = 255)
	@NotBlank
	private String fieldName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private YN enableYN;

	@Column(nullable = true)
	@Length(max = 255)
	private String comment;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Column(nullable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date updateDate;

	@NotNull
	@ManyToOne(targetEntity=Database.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "database_id", nullable = false, insertable = true, updatable = false)
	private Database database;

	@ManyToOne(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = true, insertable = true, updatable = true)
	private Member lastModifyMember;

	public DatabasePrivacyPolicy() {}

	/**
	 * @param id
	 */
	public DatabasePrivacyPolicy(Long id) {
		this.id = id;
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param enableYN
	 * @param comment
	 * @param database
	 * @param lastModifyMember
	 */
	public DatabasePrivacyPolicy(@Length(max = 255) @NotBlank String tableName,
			@Length(max = 255) @NotBlank String fieldName, @NotNull YN enableYN,
			@Length(max = 255) @NotBlank String comment, @NotNull Database database, @NotNull Member lastModifyMember) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
		this.database = database;
		this.lastModifyMember = lastModifyMember;
	}

	/**
	 * @param id
	 * @param tableName
	 * @param fieldName
	 * @param enableYN
	 * @param comment
	 * @param createDate
	 * @param updateDate
	 * @param database
	 * @param lastModifyMember
	 */
	public DatabasePrivacyPolicy(Long id, @Length(max = 255) @NotBlank String tableName,
			@Length(max = 255) @NotBlank String fieldName, @NotNull YN enableYN,
			@Length(max = 255) @NotBlank String comment, Date createDate, Date updateDate, @NotNull Database database,
			@NotNull Member lastModifyMember) {
		this.id = id;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.database = database;
		this.lastModifyMember = lastModifyMember;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public YN getEnableYN() {
		return enableYN;
	}

	public void setEnableYN(YN enableYN) {
		this.enableYN = enableYN;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Member getLastModifyMember() {
		return lastModifyMember;
	}

	public void setLastModifyMember(Member lastModifyMember) {
		this.lastModifyMember = lastModifyMember;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabasePrivacyPolicy other = (DatabasePrivacyPolicy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
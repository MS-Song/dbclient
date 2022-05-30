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

import com.song7749.common.base.Entities;
import com.song7749.common.base.YN;
import com.song7749.member.domain.Member;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <pre>
 * Class Name : PrivacyPolicy.java
 * Description : 개인정보 데이터 정의
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 10. 5.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 10. 5.
*/
@Entity
@SelectBeforeUpdate(true)
@DynamicUpdate(true)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"tableName","fieldName"}, name = "UK_PRIVACY_POLICY") })
public class PrivacyPolicy  extends Entities {

	private static final long serialVersionUID = 1182429146028562053L;

	@Id
	@Column(name="privacy_policy_id", nullable=false, updatable = false)
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(nullable = true)
	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@ManyToOne(targetEntity=Member.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = true, insertable = true, updatable = true)
	private Member lastModifyMember;

	public PrivacyPolicy() {}

	/**
	 * @param id
	 */
	public PrivacyPolicy(Long id) {
		this.id = id;
	}

	/**
	 * @param tableName
	 * @param fieldName
	 * @param enableYN
	 * @param comment
	 * @param lastModifyMember
	 */
	public PrivacyPolicy(@Length(max = 255) @NotBlank String tableName,
			@Length(max = 255) @NotBlank String fieldName, @NotNull YN enableYN,
			@Length(max = 255) @NotBlank String comment, @NotNull Member lastModifyMember) {
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
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
	 * @param lastModifyMember
	 */
	public PrivacyPolicy(Long id, @Length(max = 255) @NotBlank String tableName,
			@Length(max = 255) @NotBlank String fieldName, @NotNull YN enableYN,
			@Length(max = 255) @NotBlank String comment, Date createDate, Date updateDate,
			@NotNull Member lastModifyMember) {
		this.id = id;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.enableYN = enableYN;
		this.comment = comment;
		this.createDate = createDate;
		this.updateDate = updateDate;
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
		PrivacyPolicy other = (PrivacyPolicy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
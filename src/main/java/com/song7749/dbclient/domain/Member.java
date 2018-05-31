package com.song7749.dbclient.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.Entities;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.value.MemberVo;
import com.song7749.util.crypto.CryptoTwoWayConverter;

/**
 * <pre>
 * Class Name : Member.java
 * Description : 회원 Entity
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 1. 23.		song7749@gmail.com		NEW
 *
 * </pre>
 *
 * @author song7749@gmail.com
 * @since 2018. 1. 23.
 */

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "loginId", name = "UK_MEMBER_LOGIN_ID") })
@DynamicUpdate(true)
public class Member extends Entities {

	private static final long serialVersionUID = 474942474313960087L;

	@Id
	@Column(name="member_id", nullable=false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, updatable = false)
	@Email
	@NotBlank
	private String loginId;

	@Column(nullable = true, updatable = true)
	@Length(max = 255)
	private String apikey;

	@Column(nullable = false)
	@Length(min = 8, max = 255)
	@NotBlank
//	@ColumnTransformer( write="AES(?)", read="AES(?,Key)" ) -- DB 의 password 함수를 이용할 때..
	@Convert(converter=CryptoTwoWayConverter.class)
	private String password;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 6, max = 120)
	private String passwordQuestion;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 6, max = 120)
	private String passwordAnswer;

	@Column(nullable = false)
	@Length(max = 60)
	@NotBlank
	private String teamName;

	@Column(nullable = false)
	@Length(max = 60)
	@NotBlank
	private String name;

	@Column(nullable=true)
	@Enumerated(EnumType.STRING)
	private AuthType authType;

	@Column(nullable = true)
	@Length(max = 30)
	@Convert(converter=CryptoTwoWayConverter.class)
	private String mobileNumber;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Column(nullable = true)
	//@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date modifyDate;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	private Date lastLoginDate;

	@OneToMany(mappedBy="member",fetch=FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval=true)
	@BatchSize(size=10)
	private List<MemberDatabase> memberDatabaseList = new ArrayList<MemberDatabase>();

	@OneToMany(mappedBy="member",fetch=FetchType.LAZY,cascade=CascadeType.ALL, orphanRemoval=true)
	@BatchSize(size=10)
	private List<MemberSaveQuery> memberSaveQueryList = new ArrayList<MemberSaveQuery>();

	public Member() {}

	/**
	 * @param id
	 */
	public Member(Long id) {
		this.id = id;
	}

	/**
	 * @param loginId
	 * @param password
	 * @param passwordQuestion
	 * @param passwordAnswer
	 * @param teamName
	 * @param name
	 * @param authType
	 */
	public Member(@Email @NotBlank String loginId, @Length(min = 8, max = 20) @NotBlank String password,
			@NotBlank @Size(min = 6, max = 120) String passwordQuestion,
			@NotBlank @Size(min = 6, max = 120) String passwordAnswer, @Length(max = 60) @NotBlank String teamName,
			@Length(max = 60) @NotBlank String name, AuthType authType) {
		this.loginId = loginId;
		this.password = password;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
		this.teamName = teamName;
		this.name = name;
		this.authType = authType;
	}

	/**
	 * @param loginId
	 * @param password
	 */
	public Member(@Email @NotBlank String loginId, @Length(min = 8, max = 20) @NotBlank String password) {
		this.loginId = loginId;
		this.password = password;
	}

	/**
	 * @param certificationKey
	 */
	public Member(String apikey) {
		this.apikey = apikey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordQuestion() {
		return passwordQuestion;
	}

	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}

	public String getPasswordAnswer() {
		return passwordAnswer;
	}

	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public List<MemberDatabase> getMemberDatabaseList() {
		return memberDatabaseList;
	}

	public void setMemberDatabaseList(List<MemberDatabase> memberDatabaseList) {
		this.memberDatabaseList = memberDatabaseList;
	}

	public void addMemberDatabaseList(MemberDatabase memberDatabase) {

		if(memberDatabase == null){
			throw new IllegalArgumentException("memberDatabase must be not null");
		}
		memberDatabase.setMember(this);
		this.memberDatabaseList.add(memberDatabase);
	}

	public List<MemberSaveQuery> getMemberSaveQueryList() {
		return memberSaveQueryList;
	}

	public void setMemberSaveQueryList(List<MemberSaveQuery> memberSaveQueryList) {
		this.memberSaveQueryList = memberSaveQueryList;
	}

	public void addMemberSaveQueryList(MemberSaveQuery memberSaveQuery) {

		if(memberSaveQuery == null){
			throw new IllegalArgumentException("memberSaveQuery must be not null");
		}
		memberSaveQuery.setMember(this);
		this.memberSaveQueryList.add(memberSaveQuery);
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public MemberVo getMemberVo(ModelMapper mapper) {
		return mapper.map(this, MemberVo.class);
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
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
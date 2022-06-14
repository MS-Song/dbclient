package com.song7749.member.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.base.Entities;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.MemberVo;
import com.song7749.util.crypto.CryptoTwoWayConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = { 
	@UniqueConstraint(columnNames = "loginId", name = "UK_MEMBER_LOGIN_ID") 
})
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
	private Date createDate;

	@Column(nullable = true)
	//@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyDate;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginDate;

	/**
	 * @param id
	 */
	public Member(Long id) {
		this.id = id;
	}

	public MemberVo getMemberVo(ModelMapper mapper) {
		return mapper.map(this, MemberVo.class);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return Objects.equals(id, other.id);
	}


}
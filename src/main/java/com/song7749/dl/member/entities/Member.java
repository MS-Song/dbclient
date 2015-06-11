package com.song7749.dl.member.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.song7749.dl.base.Entities;
import com.song7749.dl.member.type.AuthType;
import com.song7749.util.crypto.CryptoAES;
import com.song7749.util.validate.ValidateGroupInsert;
import com.song7749.util.validate.ValidateGroupUpdate;

/**
 * <pre>
 * Class Name : Member.java
 * Description : 회원정보
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
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "id",name="UK_id")
})
public class Member extends Entities {

	private static final long serialVersionUID = 2498909624760963269L;

	@Id
	@NotNull
	@Size(min=8,max=20)
	private String id;

	@NotNull(groups={ValidateGroupInsert.class
			,ValidateGroupUpdate.class})
//	@ColumnTransformer( write="seal(?)", read="unseal(?)" )
	@Size(min=8,max=64
		,groups={ValidateGroupInsert.class
				,ValidateGroupUpdate.class})
	private String password;

	@Column
	@NotNull(groups={ValidateGroupInsert.class})
	@Email(groups={ValidateGroupInsert.class,ValidateGroupUpdate.class})
	private String email;

	@Column
	@NotNull(groups={ValidateGroupInsert.class,ValidateGroupUpdate.class})
	@Size(min=6,max=50
	,groups={ValidateGroupInsert.class
			,ValidateGroupUpdate.class})
	private String passwordQuestion;

	@Column
	@NotNull(groups={ValidateGroupInsert.class,ValidateGroupUpdate.class})
	@Size(min=6,max=50
	,groups={ValidateGroupInsert.class
			,ValidateGroupUpdate.class})
	private String passwordAnswer;

	@Column
	private AuthType authType;

	public Member() {}

	public Member(String id) {
		this.id = id;
	}

	public Member(String id, String password) {
		this.id = id;
		setPassword(password);
	}

	public Member(String id,String passwordQuestion, String passwordAnswer) {
		this.id = id;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

	public Member(String id, String password, String email,
			String passwordQuestion, String passwordAnswer) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}

	public Member(String id, String password, String email,
			String passwordQuestion, String passwordAnswer, AuthType authType) {
		this.id = id;
		this.password = password;
		this.email = email;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
		this.authType = authType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return CryptoAES.decrypt(password);
	}

	public void setPassword(String password) {
		this.password = CryptoAES.encrypt(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}
}
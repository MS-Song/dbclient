package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;
import com.song7749.dl.member.type.AuthType;

/**
 * <pre>
 * Class Name : ModifyMemberDTO.java
 * Description : 회원 수정 DTO
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 4. 29.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 4. 29.
*/
public class ModifyMemberDTO extends AbstractDto{

	private static final long serialVersionUID = -8973962952713127994L;


	@NotNull
	private String id;


	private String password;


	private String email;


	private String passwordQuestion;


	private String passwordAnswer;


	private AuthType authType;

	public ModifyMemberDTO() {}

	public ModifyMemberDTO(String id, String password, String email,
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
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
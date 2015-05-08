package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;

import com.song7749.dl.base.AbstractDto;
import com.song7749.dl.member.type.AuthType;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name :  AddMemberDTO.java
 * Description : 회원 생성 DTO
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
@ApiModel
public class AddMemberDTO extends AbstractDto{

	private static final long serialVersionUID = 7979771551393878737L;

	@NotNull
	@ApiModelProperty(value="유저 ID",required=true)
	private String id;

	@NotNull
	private String password;

	@NotNull
	private String email;

	@NotNull
	private String passwordQuestion;

	@NotNull
	private String passwordAnswer;

	@NotNull
	private AuthType authType;

	public AddMemberDTO() {}

	public AddMemberDTO(String id, String password, String email,
			String passwordQuestion, String passwordAnswer, AuthType authType) {
		super();
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

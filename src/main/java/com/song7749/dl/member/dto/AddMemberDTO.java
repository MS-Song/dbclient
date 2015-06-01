package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
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
@ApiModel(value="회원 생성 DTO")
public class AddMemberDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = 7979771551393878737L;

	@NotNull
	@Size(min=8,max=20)
	@ApiModelProperty(value="ID",required=true)
	private String id;

	@NotNull
	@Email
	@ApiModelProperty(value="e-mail",required=true)
	private String email;

	@NotNull
	@Size(min=8,max=20)
	@ApiModelProperty(value="비밀번호",required=true)
	private String password;

	@NotNull
	@Size(min=6,max=50)
	@ApiModelProperty(value="비밀번호 찾기 질문",required=true)
	private String passwordQuestion;

	@NotNull
	@Size(min=6,max=50)
	@ApiModelProperty(value="비밀번호 찾기 답변",required=true)
	private String passwordAnswer;

	public AddMemberDTO() {}

	public AddMemberDTO(String id, String password, String email,
			String passwordQuestion, String passwordAnswer) {
		super();
		this.id = id;
		this.password = password;
		this.email = email;
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
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

}
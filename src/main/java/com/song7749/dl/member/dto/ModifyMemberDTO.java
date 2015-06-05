package com.song7749.dl.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.song7749.dl.base.BaseObject;
import com.song7749.dl.base.Dto;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

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
@ApiModel("회원 정보 수정 DTO")
public class ModifyMemberDTO extends BaseObject implements Dto {

	private static final long serialVersionUID = -8973962952713127994L;


	@NotNull
	@Size(min=8,max=20)
	@ApiModelProperty(value="ID",required=true)
	private String id;

	@ApiModelProperty(value="e-mail")
	private String email;

	@ApiModelProperty(value="패스워드")
	private String password;

	@ApiModelProperty(value="패스워드 유효성 검증")
	private String passwordRepeat;

	@ApiModelProperty(value="패스워드 찾기 질문")
	private String passwordQuestion;

	@ApiModelProperty(value="패스워드 찾기 답변")
	private String passwordAnswer;

	public ModifyMemberDTO() {}

	public ModifyMemberDTO(String id, String email, String password,
			String passwordRepeat, String passwordQuestion,
			String passwordAnswer) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.passwordRepeat = passwordRepeat;
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

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
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
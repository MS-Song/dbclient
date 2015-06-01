package com.song7749.dl.member.vo;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import com.song7749.dl.base.AbstractVo;
import com.song7749.dl.member.type.AuthType;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * Class Name : MemberVO.java
 * Description : 회원 VO 객체
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
@ApiModel("회원정보")
@JsonPropertyOrder({ "id",
	"email",
	"passwordQuestion",
	"authType" })
public class MemberVO extends AbstractVo{

	private static final long serialVersionUID = -5590670872135422125L;

	@ApiModelProperty(value="ID")
	private String id;
	@ApiModelProperty(value="e-mail")
	private String email;
	@ApiModelProperty(value="비밀번호 찾기 질문")
	private String passwordQuestion;
	@ApiModelProperty(value="회원권한")
	private AuthType authType;

	public MemberVO() {}

	public MemberVO(String id, String email,
			String passwordQuestion, AuthType authType) {
		super();
		this.id = id;
		this.email = email;
		this.passwordQuestion = passwordQuestion;
		this.authType = authType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}
}

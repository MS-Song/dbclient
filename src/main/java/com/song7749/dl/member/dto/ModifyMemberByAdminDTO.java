package com.song7749.dl.member.dto;

import com.song7749.dl.member.type.AuthType;
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
@ApiModel("관리자 회원 정보 수정 DTO")
public class ModifyMemberByAdminDTO extends ModifyMemberDTO{

	private static final long serialVersionUID = -7983016814476656433L;

	@ApiModelProperty(value="회원 권한")
	private AuthType authType;

	public ModifyMemberByAdminDTO() {}

	public ModifyMemberByAdminDTO(String id, String email, String password,
			String passwordRepeat, String passwordQuestion,
			String passwordAnswer) {
		super(id, email, password, passwordRepeat, passwordQuestion, passwordAnswer);
	}

	public ModifyMemberByAdminDTO(String id, String email, String password,
			String passwordRepeat, String passwordQuestion,
			String passwordAnswer, AuthType authType) {

		super(id, email, password, passwordRepeat, passwordQuestion, passwordAnswer);
		this.authType = authType;
	}

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}
}
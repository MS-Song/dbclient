package com.song7749.dl.member.dto;

import com.song7749.dl.base.AbstractDto;
import com.song7749.dl.member.type.AuthType;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel
public class FindMemberListDTO extends AbstractDto{

	private static final long serialVersionUID = 3495143635493246020L;

	@ApiModelProperty("회원ID")
	private String id;

	@ApiModelProperty("회원 e-mail")
	private String email;

	@ApiModelProperty("회원 권한")
	private AuthType authType;

	/**
	 * 기본 생성자
	 */
	public FindMemberListDTO() {}

	/**
	 * ID 생성자
	 * @param id
	 */
	public FindMemberListDTO(String id) {
		this.id = id;
	}

	/**
	 * 권한 생성자
	 * @param authType
	 */
	public FindMemberListDTO(AuthType authType) {
		this.authType = authType;
	}

	/**
	 * 전체 생성자
	 * @param id
	 * @param email
	 * @param authType
	 */
	public FindMemberListDTO(String id, String email, AuthType authType) {
		this.id = id;
		this.email = email;
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

	public AuthType getAuthType() {
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}


}

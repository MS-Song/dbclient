package com.song7749.dbclient.drs.value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.song7749.base.BaseObject;
import com.song7749.base.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("API 인증키를 발급 받거나 갱신한다.")
public class RenewApikeyDto extends BaseObject implements Dto{

	private static final long serialVersionUID = -3432687295709638793L;

	@ApiModelProperty(value="로그인ID",required=true,example="email 만 입력 가능합니다.")
	@Email
	@NotBlank
	private String loginId;

	@ApiModelProperty(value = "패스워드", required = true, example = "8~20 자 이내로 영문+특수문자 조합으로 입력하세요")
	@Length(min = 8, max = 20)
	@NotBlank
	private String password;

	public RenewApikeyDto() {}

	/**
	 * @param loginId
	 * @param password
	 */
	public RenewApikeyDto(@Email @NotBlank String loginId, @Length(min = 8, max = 20) String password) {
		this.loginId = loginId;
		this.password = password;
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
}
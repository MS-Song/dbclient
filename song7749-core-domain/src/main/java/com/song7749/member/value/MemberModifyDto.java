package com.song7749.member.value;

import javax.validation.constraints.NotNull;

import com.song7749.common.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("회원수정")
public class MemberModifyDto  extends AbstractDto {

	private static final long serialVersionUID = -3526116343828894825L;

	@ApiModelProperty(value = "회원번호", required = true)
	@NotNull
	private Long id;

	@ApiModelProperty(value = "패스워드", example = "8~20 자 이내로 영문+특수문자 조합으로 입력하세요")
	private String password;

	@ApiModelProperty(value = "패스워드 찾기 질문", example = "6~120 자 로 입력하세요")
	private String passwordQuestion;

	@ApiModelProperty(value = "패스워드 찾기 답변", example = "6~120 자 로 입력하세요")
	private String passwordAnswer;

	@ApiModelProperty(value = "팀명", example = "60자 이내로 입력하세요")
	private String teamName;

	@ApiModelProperty(value = "성명",example = "60자 이내로 입력하세요")
	private String name;

	@ApiModelProperty("핸드폰 번호")
	private String mobileNumber;

	/**
	 * @param id
	 */
	public MemberModifyDto(@NotNull Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param password
	 */
	public MemberModifyDto(@NotNull Long id, String password) {
		this.id = id;
		this.password = password;
	}
}
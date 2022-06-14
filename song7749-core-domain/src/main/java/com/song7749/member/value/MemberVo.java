package com.song7749.member.value;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.song7749.common.base.AbstractVo;
import com.song7749.member.type.AuthType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author song7749@gmail.com
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("회원정보")
public class MemberVo extends AbstractVo {

	private static final long serialVersionUID = -1825545819541825666L;

	@ApiModelProperty("회원번호")
	private Long id;

	@ApiModelProperty("로그인 ID")
	private String loginId;

	@ApiModelProperty("인증키")
	private String apikey;

	@ApiModelProperty("패스워드 찾기 질문")
	private String passwordQuestion;

	@ApiModelProperty("팀명칭")
	private String teamName;

	@ApiModelProperty("성명")
	private String name;

	@ApiModelProperty("전화번호")
	private String mobileNumber;

	@ApiModelProperty("권한")
	private AuthType authType;

	@ApiModelProperty("가입일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;

	@ApiModelProperty("회원정보 수정일")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date modifyDate;

	@ApiModelProperty("최근 로그인 일시")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date lastLoginDate;
}
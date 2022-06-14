package com.song7749.member.value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.song7749.common.base.AbstractDto;
import com.song7749.member.domain.Member;

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
@ApiModel("회원등록")
public class MemberAddDto  extends AbstractDto {

	private static final long serialVersionUID = 4870923370488884811L;

	@ApiModelProperty(value="로그인ID",required=true,example="email 만 입력 가능합니다.")
	@Email
	@NotBlank
	private String loginId;

	@ApiModelProperty(value="패스워드",required=true,example="8~20 자 이내로 영문+특수문자 조합으로 입력하세요")
	@Length(min = 8, max = 20)
	@NotBlank
	private String password;

	@ApiModelProperty(value="패스워드 찾기 질문",required=true,example="6~120 자 로 입력하세요")
	@NotBlank
	@Size(min = 6, max = 120)
	private String passwordQuestion;

	@ApiModelProperty(value="패스워드 찾기 답변",required=true,example="6~120 자 로 입력하세요")
	@NotBlank
	@Size(min = 6, max = 120)
	private String passwordAnswer;

	@ApiModelProperty(value="팀명" ,required=true,example="60자 이내로 입력하세요")
	@Length(max = 60)
	@NotBlank
	private String teamName;

	@ApiModelProperty(value="성명",required=true,example="60자 이내로 입력하세요")
	@Length(max = 60)
	@NotBlank
	private String name;

	@ApiModelProperty(value="핸드폰 번호")
	@Length(min = 10, max = 14)
	private String mobileNumber;

	public Member getMember(ModelMapper mapper) {
		return mapper.map(this, Member.class);
	}
}
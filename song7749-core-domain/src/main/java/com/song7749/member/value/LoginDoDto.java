package com.song7749.member.value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.song7749.common.base.AbstractDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : DoLoginDTO.java
 * Description : 로그인 실행 DTO 객체
*
*  Modification Information
*  Modify Date 		Modifier	Comment
*  -----------------------------------------------
*  2015. 5. 13.		song7749	신규작성
*
* </pre>
*
* @author song7749
* @since 2015. 5. 13.
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class LoginDoDto  extends AbstractDto {

	private static final long serialVersionUID = 2494197854525312202L;

	@Email
	@NotBlank
	@ApiModelProperty(value="로그인 id",required=true)
	private String loginId;

	@NotBlank
	@Size(min=8,max=20)
	@ApiModelProperty(value="비밀번호",required=true)
	private String password;
}
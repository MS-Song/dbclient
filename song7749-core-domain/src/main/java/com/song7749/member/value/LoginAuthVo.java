package com.song7749.member.value;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.base.AbstractVo;
import com.song7749.member.type.AuthType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <pre>
 * Class Name : LoginAuthVo.java
 * Description : 회원 인증 정보 객체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 3. 9.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 3. 9.
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAuthVo extends AbstractVo {

	private static final long serialVersionUID = -4016069181069163117L;

	private Long id;

	private String loginId;

	private String ip;

	private AuthType authType;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date create;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date refresh;

	/**
	 * @param id
	 */
	public LoginAuthVo(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param authType
	 */
	public LoginAuthVo(Long id, AuthType authType) {
		this.id = id;
		this.authType = authType;
	}

	/**
	 * @param loginId
	 * @param authType
	 */
	public LoginAuthVo(String loginId, AuthType authType) {
		this.loginId = loginId;
		this.authType = authType;
	}


	/**
	 * @param id
	 * @param loginId
	 * @param authType
	 * @param sessionCreateDate
	 */
	public LoginAuthVo(Long id, String loginId, AuthType authType, Date create) {
		this.id = id;
		this.loginId = loginId;
		this.authType = authType;
		this.create = create;
	}
}
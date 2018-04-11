package com.song7749.dbclient.drs.value;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.base.AbstractVo;
import com.song7749.dbclient.drs.type.AuthType;

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
public class LoginAuthVo extends AbstractVo {

	private static final long serialVersionUID = -4016069181069163117L;

	private Long id;

	private String loginId;

	private String ip;

	private AuthType authType;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date create;

	@DateTimeFormat(pattern = "yyyy-MM-dd h:i:s")
	private Date refresh;

	public LoginAuthVo() {}

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

	/**
	 * @param id
	 * @param loginId
	 * @param ip
	 * @param authType
	 * @param create
	 * @param refresh
	 */
	public LoginAuthVo(Long id, String loginId, String ip, AuthType authType, Date create, Date refresh) {
		this.id = id;
		this.loginId = loginId;
		this.ip = ip;
		this.authType = authType;
		this.create = create;
		this.refresh = refresh;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public AuthType getAuthType() {
		if(null==authType)
			throw new IllegalArgumentException("권한 부여가 되지 않아 접근이 불가능 합니다.");
		return authType;
	}

	public void setAuthType(AuthType authType) {
		this.authType = authType;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getRefresh() {
		return refresh;
	}

	public void setRefresh(Date refresh) {
		this.refresh = refresh;
	}
}
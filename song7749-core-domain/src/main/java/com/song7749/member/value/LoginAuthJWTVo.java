package com.song7749.member.value;

import java.util.Date;

import com.song7749.common.base.AbstractVo;
import com.song7749.member.domain.Member;
import com.song7749.member.type.AuthType;

import org.modelmapper.ModelMapper;

/**
 * <pre>
 * Class Name : LoginAuthVoJWT.java
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
public class LoginAuthJWTVo extends AbstractVo {

	private static final long serialVersionUID = -4016069181069163117L;

	/**
	 * 	iss: 토큰을 발급한 발급자(Issuer)
	 */
	private String iss;
	/**
	 * 	sub: Claim의 주제(Subject)로 토큰이 갖는 문맥을 의미한다.
	 */
	private String sub;
	/**
	 *  aud: 이 토큰을 사용할 수신자(Audience)
	 */
	private String aud;;
	/**
	 * 	exp: 만료시간(Expiration Time)은 만료시간이 지난 토큰은 거절해야 한다.
	 */
	private Date exp;
	/**
	 * 	nbf: Not Before의 의미로 이 시간 이전에는 토큰을 처리하지 않아야 함을 의미한다.
	 */
	private Date nbf;
	/**
	 * iat: 토큰이 발급된 시간(Issued At)
	 */
	private Date iat;
	/**
	 * jti: JWT ID로 토큰에 대한 식별자이다.
	 */
	private String jti;

	/**
	 * member id
	 */
	private Long id;

	/**
	 * member login id
	 */
	private String loginId;

	/**
	 * connect ip address
	 */
	private String ip;

	/**
	 * auth type
	 */
	private AuthType authType;

	public LoginAuthJWTVo() {}

	/**
	 * @param id
	 */
	public LoginAuthJWTVo(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param authType
	 */
	public LoginAuthJWTVo(Long id, AuthType authType) {
		this.id = id;
		this.authType = authType;
	}

	/**
	 * @param loginId
	 * @param authType
	 */
	public LoginAuthJWTVo(String loginId, AuthType authType) {
		this.loginId = loginId;
		this.authType = authType;
	}

	/**
	 * @param id
	 * @param loginId
	 * @param ip
	 * @param authType
	 * @param iss: 토큰을 발급한 발급자(Issuer)
	 * @param sub: Claim의 주제(Subject)로 토큰이 갖는 문맥을 의미한다.
	 * @param aud: 이 토큰을 사용할 수신자(Audience)
	 * @param exp: 만료시간(Expiration Time)은 만료시간이 지난 토큰은 거절해야 한다.
	 * @param nbf: Not Before의 의미로 이 시간 이전에는 토큰을 처리하지 않아야 함을 의미한다.
	 * @param iat: 토큰이 발급된 시간(Issued At)
	 * @param jti: JWT ID로 토큰에 대한 식별자이다.
	 */
	public LoginAuthJWTVo(Long id, String loginId, String ip, AuthType authType, String iss, String sub, String aud,
			Date exp, Date nbf, Date iat, String jti) {
		this.id = id;
		this.loginId = loginId;
		this.ip = ip;
		this.authType = authType;
		this.iss = iss;
		this.sub = sub;
		this.aud = aud;
		this.exp = exp;
		this.nbf = nbf;
		this.iat = iat;
		this.jti = jti;
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
	/**
	 * iss: 토큰을 발급한 발급자(Issuer)
	 * @return
	 */
	public String getIss() {
		return iss;
	}

	/**
	 * @param iss iss: 토큰을 발급한 발급자(Issuer)
	 */
	public void setIss(String iss) {
		this.iss = iss;
	}

	/**
	 * sub: Claim의 주제(Subject)로 토큰이 갖는 문맥을 의미한다.
	 * @return
	 */
	public String getSub() {
		return sub;
	}

	/**
	 * @param sub: Claim의 주제(Subject)로 토큰이 갖는 문맥을 의미한다.
	 */
	public void setSub(String sub) {
		this.sub = sub;
	}

	/**
	 * aud: 이 토큰을 사용할 수신자(Audience)
	 * @return
	 */
	public String getAud() {
		return aud;
	}

	/**
	 * @param aud: 이 토큰을 사용할 수신자(Audience)
	 */
	public void setAud(String aud) {
		this.aud = aud;
	}

	/**
	 * exp: 만료시간(Expiration Time)은 만료시간이 지난 토큰은 거절해야 한다.
	 * @return
	 */
	public Date getExp() {
		return exp;
	}

	/**
	 * @param exp: 만료시간(Expiration Time)은 만료시간이 지난 토큰은 거절해야 한다.
	 */
	public void setExp(Date exp) {
		this.exp = exp;
	}

	/**
	 * nbf: Not Before의 의미로 이 시간 이전에는 토큰을 처리하지 않아야 함을 의미한다.
	 * @return
	 */
	public Date getNbf() {
		return nbf;
	}

	/**
	 * @param nbf: Not Before의 의미로 이 시간 이전에는 토큰을 처리하지 않아야 함을 의미한다.
	 */
	public void setNbf(Date nbf) {
		this.nbf = nbf;
	}

	/**
	 * iat: 토큰이 발급된 시간(Issued At)
	 * @return
	 */
	public Date getIat() {
		return iat;
	}

	/**
	 * @param iat: 토큰이 발급된 시간(Issued At)
	 */
	public void setIat(Date iat) {
		this.iat = iat;
	}

	/**
	 * jti: JWT ID로 토큰에 대한 식별자이다.
	 * @return
	 */
	public String getJti() {
		return jti;
	}

	/**
	 * @param jti: JWT ID로 토큰에 대한 식별자이다.
	 */
	public void setJti(String jti) {
		this.jti = jti;
	}

	public Member getMember(ModelMapper mapper) {
		return mapper.map(this, Member.class);
	}
}
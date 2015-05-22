package com.song7749.dl.login.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.dl.login.annotations.Login;
import com.song7749.dl.login.dto.DoLoginDTO;
import com.song7749.dl.member.entities.Member;
import com.song7749.dl.member.entities.MemberAuth;
import com.song7749.dl.member.repositories.MemberRepository;
import com.song7749.dl.member.type.AuthType;
import com.song7749.util.crypto.CryptoAES;

/**
 * <pre>
 * Class Name : CertificationLoginUtil.java
 * Description : 인증서버 로그인 유틸
*
*  Modification Information
*  Modify Date 		Modifier	Comment
* -----------------------------------------------
*  2014. 3. 14.		song7749	AP-20
*
* </pre>
*
* @author song7749
* @since 2014. 3. 14.
*/
@Service("loginManager")
public class LoginManagerImpl implements LoginManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	// 로그인 정보 저장 쿠키 명칭
	private final String cipher = "cipher";

	@Autowired
	MemberRepository memberRepository;

	@Override
	public boolean isLogin(HttpServletRequest request) {
		String id = getLoginID(request);
		if(null!=id){
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Valid
	@Transactional(value = "dbClientTransactionManager",readOnly=true)
	public boolean doLogin(DoLoginDTO dto,HttpServletResponse response){
		Member findMember = new Member(dto.getId());
		Member member = memberRepository.find(findMember);

		// 회원 정보가 조회가 되면.. 회원이 존재함.
		if(null != member){
			// 패스워드가 동일 함
			if(dto.getPassword().equals(member.getPassword())){
				// 로그인 cookie 정보를 생성 한다.
				Cookie ciperCookie = new Cookie(cipher,CryptoAES.encrypt(member.getId()));
				ciperCookie.setMaxAge(60*60*2);
				response.addCookie(ciperCookie);
				return true;
			} else {
				throw new IllegalArgumentException("password=PASSWORD 가 일치하지 않습니다.");
			}
		}
		throw new IllegalArgumentException("id=존재하지 않는 ID 입니다.");
	}

	@Override
	public void doLogout(HttpServletResponse response) {
		response.addCookie(new Cookie(cipher,""));
	}

	@Override
	public String getLoginID(HttpServletRequest request) {
		String cipher = null;
		Cookie[] cookie = request.getCookies();
		// 쿠키에서 cipher 를 찾아낸다.
		if (cookie != null) {
			for (int i=0; i<cookie.length; i++) {
				if (null != cookie[i]
						&& cookie[i].getName().equals(this.cipher)) {
					cipher = cookie[i].getValue();
					// 복호화 된 ID 정보를 리턴한다.
					return CryptoAES.decrypt(cipher);
				}
			}
		}
		return null;
	}

	/**
	 * 해당 회원이 접근 가능한 기능인가 검증
	 * @param member
	 * @param login
	 * @return boolean
	 */
	@Override
	@Transactional(value = "dbClientTransactionManager",readOnly=true)
	public boolean isAccese(HttpServletRequest request,Login login) {
		// 일반 회원 접근 가능 페이지는 권한 체크 하지 않는다.
		if(login.value().equals(AuthType.NORMAL)){
			return true;
		} else {
			// 회원 로그인 정보에서 데이터를 가져와서 권한 여부를 판단한다.
			Member member = memberRepository.find(new Member(getLoginID(request)));

			if(null!=member && null!=login){
				for(MemberAuth ma : member.getMemberAuthList()){
					if(ma.getAuthType().equals(login.value())){
						return true;
					}
				}
			}
			return false;
		}
	}

}
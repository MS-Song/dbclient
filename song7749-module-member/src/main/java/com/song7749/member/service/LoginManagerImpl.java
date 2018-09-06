package com.song7749.member.service;

import static com.song7749.util.LogMessageFormatter.format;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song7749.log.service.LogManager;
import com.song7749.log.value.LogLoginAddDto;
import com.song7749.member.annotation.Login;
import com.song7749.member.domain.Member;
import com.song7749.member.repository.MemberRepository;
import com.song7749.member.type.AuthType;
import com.song7749.member.value.LoginAuthVo;
import com.song7749.member.value.LoginDoDto;
import com.song7749.util.ObjectJsonUtil;
import com.song7749.util.StringUtils;
import com.song7749.util.crypto.CryptoAES;


/**
 * <pre>
 * Class Name : LoginManagerImpl.java
 * Description : 로그인 매니저 구현체
*
*
*  Modification Information
*  Modify Date 		Modifier				Comment
*  -----------------------------------------------
*  2018. 2. 28.		song7749@gmail.com		NEW
*
* </pre>
*
* @author song7749@gmail.com
* @since 2018. 2. 28.
*/
@Service("loginManager")
@PropertySource(value = {"classpath:/member-config.properties"})
public class LoginManagerImpl implements LoginManager{

	Logger logger = LoggerFactory.getLogger(getClass());

	// 로그인 정보 저장 쿠키 명칭
	private final String cipher = "cipher";

	@Value("${app.login.session.timeout}")
	private Integer sessionTimeout;

	@Value("${app.login.session.checktime}")
	private Integer sessionChecktime;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberManager memberManager;

	@Autowired
	LogManager logManager;

	@Autowired
	LoginSession loginSession;

	@Autowired
	ModelMapper mapper;


	@Override
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
		return null!=getLoginID(request,response) ? true : false;
	}

	@Override
	@Valid
	@Transactional
	public boolean doLogin(LoginDoDto dto,HttpServletRequest request, HttpServletResponse response){
		Member member = memberRepository.findByLoginIdAndPassword(dto.getLoginId(), dto.getPassword());
		// 회원 정보가 조회가 되면.. 회원이 존재함.
		if(null != member
				&& null != member.getId()){

			// 마지막 로그인 시간 업데이트
			member.setLastLoginDate(new Date(System.currentTimeMillis()));
			memberRepository.saveAndFlush(member);
			// 인증쿠키 생성 및 인증 기록

			// 로그인 인증 정보를 생성한다.
			LoginAuthVo lav = mapper.map(member, LoginAuthVo.class);
			lav.setCreate(new Date(System.currentTimeMillis()));
			lav.setRefresh(lav.getCreate());
			lav.setIp(request.getRemoteAddr());

			return makeLoginCookie(lav, request, response);
		}
		throw new IllegalArgumentException("id=ID 또는 패스워드가 틀립니다.");
	}

	/**
	 * 로그인 쿠키를 생성한다.
	 * 	-- 만료 기간 전에 로그인 쿠키 재생성에도 사용된다.
	 * @param member
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean makeLoginCookie(LoginAuthVo lav,HttpServletRequest request, HttpServletResponse response) {
		// 인증 값 생성
		String cipherValue = null;
		try {
			cipherValue=CryptoAES.encrypt(ObjectJsonUtil.getJsonStringByObject(lav));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		// 인증 쿠키 생성
		Cookie cipherCookie = new Cookie(cipher,cipherValue);
		cipherCookie.setMaxAge(60*this.sessionTimeout);
		cipherCookie.setPath("/");
		response.addCookie(cipherCookie);

		// 로그인 로그 기록 -- asyc 기록
		LogLoginAddDto logLoginAddDto = new LogLoginAddDto(
				request.getRemoteAddr(),
				lav.getLoginId(),
				cipherValue);
		logManager.addLogLogin(logLoginAddDto);
		logger.debug(format("{}","Login Log"),logLoginAddDto);
		return true;
	}

	@Override
	public void doLogout(HttpServletResponse response) {
		// 인증 쿠키 값 제거
		Cookie cookie = new Cookie(cipher,"");
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public String getLoginID(HttpServletRequest request, HttpServletResponse response) {
		// 인증키가 있는 경우
		if(null!=request.getParameter("apikey")
				&& !request.getParameter("apikey").isEmpty()) {
			// 인증키를 가져온다.
			String apikey = request.getParameter("apikey");
			logger.debug(format("value : {} , size : {}","login apikey inifo"),apikey,apikey.length());

			// 인증 객체로 변경 한다.
			LoginAuthVo lav = null;
			try {
				lav = (LoginAuthVo) ObjectJsonUtil.getObjectByJsonString(CryptoAES.decrypt(apikey),LoginAuthVo.class);
				logger.debug(format("{}","Login apikey 복호화 완료"),lav);
			} catch (Exception e) {
				throw new IllegalArgumentException("apikey 정보 복호화 실패. 관리자에게 문의 하시기 바랍니다.");
			}
			// Login Session 도 생성 한다.
			try {
				if(!loginSession.isLogin()) {
					logger.debug(format("{}","apikey Session create"),lav);
					loginSession.setSesseion(lav);
				}
			} catch (Exception e) {
				logger.info(format("{}", "apikey Session Error Message"),e.getMessage());
			}

			// 인증에 대한 기록을 남겨야 하나 인증키 방식은 빈번함으로 별도의 프로세스가 필요 할 것으로 보임.

			return lav.getLoginId();

		} else { // cookie 가 있는 경우
			String cipherValue = null;
			Cookie[] cookie = request.getCookies();

			// 쿠키에서 cipher 를 찾아낸다.
			if (cookie != null) {
				for (int i=0; i<cookie.length; i++) {
					if (null != cookie[i]
							&& cookie[i].getName().equals(this.cipher)) {

						cipherValue = cookie[i].getValue();
						// 복호화 된 ID 정보를 리턴한다.
						logger.debug(format("value : {} , size : {}","login cookie inifo"),cipherValue,cipherValue.length());

						if(!StringUtils.isBlank(cipherValue) && cipherValue.length()>=24){
							LoginAuthVo lav = null;
							try {
								lav = (LoginAuthVo) ObjectJsonUtil.getObjectByJsonString(CryptoAES.decrypt(cipherValue),LoginAuthVo.class);
								logger.debug(format("{}","Login Cookie 복호화 완료"),lav);
							} catch (Exception e) {
								throw new IllegalArgumentException("로그인 정보 복호화 실패. 관리자에게 문의 하시기 바랍니다.");
							}

							// TODO session 의 IP 와 현재 IP 비교
							// session 갱신 시간 이후인 경우 DB의 인증 생성 시간과 비교 한다.
							// 인증 체크 시간 후
							boolean isSessionRefresh = lav.getRefresh().before(new Date(System.currentTimeMillis() - sessionChecktime*60*1000));
							// 인증 만료시간 전
							boolean isSessionTimeout = lav.getRefresh().after(new Date(System.currentTimeMillis() - sessionTimeout*60*1000));
							// 인증 체크시간 후 인증 만료시간 전인 경우에 인증을 갱신 한다.
							logger.debug(format("갱신 시간 후 : {} \n만료시간 전: {} ","Login Session Check"),isSessionRefresh,isSessionTimeout);

							if(isSessionRefresh && isSessionTimeout) {
								// 회원 DB 를 조회하여 인증 정보를 갱신 한다.
								Member member = memberRepository.findByLoginId(lav.getLoginId());
								// 회원 정보가 조회가 되면.. 회원이 존재함.
								if(null != member && null != member.getId()){
									// 회원 정보가 변경되었을 수 있음으로 갱신 한다.
									mapper.map(member, lav);
									// 인증 시간 갱신
									lav.setRefresh(new Date(System.currentTimeMillis()));
									// 로그인 정보 재 생성
									makeLoginCookie(lav, request, response);
									logger.debug(format("{}","Login Session refresh"),lav);
								} else {
									return null;
								}
							} else if(!isSessionTimeout) { // 만료시간 이후라면
								return null;
							}

							// Login Session 도 생성 한다.
							logger.debug(format("{}","Login Session create"),lav);
							loginSession.setSesseion(lav);
							// ID 리턴
							return null!=lav ? lav.getLoginId() : null;
						}
						break;
					}
				}
			}
			return null; // else end
		}
	}

	/**
	 * 해당 회원이 접근 가능한 기능인가 검증
	 * @param member
	 * @param login
	 * @return boolean
	 */
	@Override
	public boolean isAccese(HttpServletRequest request, HttpServletResponse response, Login login) {
		// 세션에 있는지 확인
		if(loginSession.isLogin()
				&& null != loginSession.getLogin().getAuthType()) {

			for(AuthType at : login.value()){
				if(loginSession.getLogin().getAuthType().equals(at)){
					return true;
				}
			}
		}
		return false;
	}
}
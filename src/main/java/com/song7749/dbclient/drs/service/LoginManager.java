package com.song7749.dbclient.drs.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.song7749.dbclient.annotation.Login;
import com.song7749.dbclient.drs.value.LoginDoDto;


/**
 * <pre>
 * Class Name : LoginManager.java
 * Description : 로그인 매니저
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

public interface LoginManager {

	/**
	 * 로그인 여부 확인
	 * 로그인에 성공하면 Request 객체 내에 로그인 ID 정보를 추가한다.
	 * Cookie 에 저장되어 있는 cipher 를 복호화 하여 Request 객체에 넣음으로 보안상 문제는 없음.
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public boolean isLogin(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 로그인 실행
	 * @param dto
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public boolean doLogin(LoginDoDto dto,HttpServletRequest request, HttpServletResponse response);

	/**
	 * 로그아웃 실행
	 * @param response
	 */
	public void doLogout(HttpServletResponse response);

	/**
	 * 로그은 된 회원의 ID 조회 및 인증정보 갱신
	 * LoginSession 도 생성 한다.
	 * @param request
	 * @param response
	 * @return String
	 */
	public String getLoginID(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 회원 접근 가능 여부
	 * @param request
	 * @param response
	 * @param login
	 * @return boolean
	 */
	public boolean isAccese(HttpServletRequest request, HttpServletResponse response, Login login);
}
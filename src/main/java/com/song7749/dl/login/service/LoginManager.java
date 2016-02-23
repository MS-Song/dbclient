package com.song7749.dl.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.song7749.dl.login.annotations.Login;
import com.song7749.dl.login.dto.DoLoginDTO;

/**
 * <pre>
 * Class Name : loginManager.java
 * Description : 로그인 처리 매니저
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
public interface LoginManager {

	/**
	 * 로그인 여부 확인
	 * 로그인에 성공하면 Request 객체 내에 로그인 ID 정보를 추가한다.
	 * Cookie 에 저장되어 있는 cipher 를 복호화 하여 Request 객체에 넣음으로 보안상 문제는 없음.
	 * @param request
	 * @return boolean
	 */
	public boolean isLogin(HttpServletRequest request);

	/**
	 * 로그인 실행
	 * @param dto
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public boolean doLogin(DoLoginDTO dto,HttpServletRequest request,HttpServletResponse response);

	/**
	 * 로그아웃 실행
	 * @param response
	 */
	public void doLogout(HttpServletResponse response);

	/**
	 * 로그인 된 회원의 ID 정보 조회
	 * @param request
	 * @return String loginID
	 */
	public String getLoginID(HttpServletRequest request);

	/**
	 * 해당 기능 접근 가능 여부 확인
	 * @param member
	 * @param login
	 * @return boolean
	 */
	public boolean isAccese(HttpServletRequest request,Login login);

	/**
	 * 로그인 사용자와 입력받은 ID가 동일한가 검증
	 * @param request
	 * @param id
	 * @return boolean
	 */
	public boolean isIdentification(HttpServletRequest request,String id);
}
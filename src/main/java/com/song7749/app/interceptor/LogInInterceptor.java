package com.song7749.app.interceptor;

import static com.song7749.util.LogMessageFormatter.format;

import java.io.PrintWriter;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.song7749.dl.login.annotations.Login;
import com.song7749.dl.login.exception.AuthorityUserException;
import com.song7749.dl.login.service.LoginManager;
import com.song7749.util.LogMessageFormatter;

/**
 * <pre>
 * Class Name : LogInInterceptor.java
 * Description : 로그인 제어 인터셉터
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

public class LogInInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginManager loginManager;

	@Value("#{config['login.url']}")
	String loginUrl;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		Login login = getLoginAnnotation((HandlerMethod)handler);

		/**
		 * 로그인이 필요한 경우에만 실행한다.
		 */
		if(login != null){

			logger.debug(format("로그인이 필요한 페이지에 접근했습니다.", "로그인"));

			// 로그인 되어 있지 않다. 로그인 페이지로 이동시킨다.
			if(!loginManager.isLogin(request)){
				switch(login.type()){
					case REDIRECT:
						logger.trace(LogMessageFormatter.format("로그인 페이지로 이동합니다.", "로그인"));
						response.sendRedirect(loginUrl+"?return="+request.getRequestURL());
						break;
					case MESSAGE:
						logger.trace(LogMessageFormatter.format("로그인 메세지를 출력 합니다", "로그인"));
						PrintWriter out = response.getWriter();
						out.println("<html><body>로그인이 필요합니다.</body></html>");
						break;
					case EXCEPTION:
						logger.trace(LogMessageFormatter.format("로그인 익셉션이 발생합니다.", "로그인"));
						throw new LoginException("로그인이 필요한 서비스입니다. 로그인 해주시기 바랍니다.");
				}
			}
			// 로그인이 되어 있다.
			else{
				logger.debug(LogMessageFormatter.format("로그인 처리 로직을 시작 합니다. {}", "로그인"),login);

				// 권한에 맞는 페이지에 접근 했는가 확인
				if(!loginManager.isAccese(request, login)){
					logger.trace(format("회원 권한 확인을 시작 합니다.", "로그인"));
					throw new AuthorityUserException();
				}
				logger.debug(LogMessageFormatter.format("{} 님이 로그인 했습니다.", "로그인"),loginManager.getLoginID(request));
			}
		}
		return true;
	}

	/**
	 * 로그인 애노테이션의 정보를 읽어온다.
	 * @param handlerMethod
	 * @return Login
	 */
	private Login getLoginAnnotation(HandlerMethod handlerMethod){
		// method level 인가 검증
		Login login = handlerMethod.getMethodAnnotation(Login.class);
		// null 인 경우 class level 인가 검증
		if(null == login){
			login = handlerMethod.getBean().getClass().getAnnotation(Login.class);
		}
		return login;
	}
}
package com.song7749.web;

import static com.song7749.util.LogMessageFormatter.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.song7749.base.MessageVo;
import com.song7749.dbclient.annotation.Login;
import com.song7749.dbclient.service.LoginManager;
import com.song7749.dbclient.service.LoginSession;
import com.song7749.dbclient.service.MemberManager;
import com.song7749.dbclient.type.AuthType;
import com.song7749.dbclient.value.LoginDoDto;
import com.song7749.dbclient.value.MemberVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Api(tags = "회원 로그인 기능")
@RestController
@RequestMapping("/member")
public class MemberLoginCotroller {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberManager memberManager;

	@Autowired
	LoginSession session;

	@ApiOperation(value = "회원 로그인"
			, notes = "회원 ID/PASSWORD 를 받아서 로그인 cookie 를 생성 한다."
			, response=MessageVo.class)
	@PostMapping("/doLogin")
	public MessageVo doLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute LoginDoDto dto){

		MessageVo vo = null;
		if(loginManager.doLogin(dto, request, response)) {
			vo = new MessageVo(HttpServletResponse.SC_OK, "로그인 처리가 완료되었습니다.");
		} else {
			vo = new MessageVo(HttpServletResponse.SC_FORBIDDEN, "로그인에 실패 했습니다.");
		}
		return vo;
	}

	@ApiOperation(value = "회원 로그아웃"
			, notes = "로그 아웃 프로세스를 실행한다."
			, response=MessageVo.class)
	@PostMapping("/doLogout")
	public MessageVo doLogout(HttpServletResponse response){

		loginManager.doLogout(response);
		return new MessageVo(HttpServletResponse.SC_OK, "로그아웃 처리가 완료되었습니다.");
	}


	@ApiOperation(value = "로그인 정보 획득"
			, notes = "cookie 에 저장되어 있는 로그인 정보를 획득한다."
			, response=MemberVo.class)
	@GetMapping("/getLogin")
	@Login({ AuthType.NORMAL, AuthType.ADMIN })
	public MemberVo getLogin(HttpServletRequest request, HttpServletResponse response){
		logger.trace(format("{}", "Login Session Login ID"),session.getLogin().getLoginId());
		return memberManager.findMember(session.getLogin().getLoginId());
	}
}
package com.song7749.app.dbclient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.song7749.dl.base.ResponseResult;
import com.song7749.dl.login.dto.DoLoginDTO;
import com.song7749.dl.login.service.LoginManager;
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.service.MemberManager;
import com.song7749.dl.member.vo.MemberVO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Api(value = "login", description = "login 관련 기능",position=3)
@Controller
@RequestMapping("/member")
public class MemberLoginCotroller {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginManager loginManager;

	@Autowired
	MemberManager memberManager;

	@ApiOperation(value = "회원 로그인"
			,notes = "회원 ID/PASSWORD 를 받아서 로그인 cookie 를 생성 한다."
			,response=ResponseResult.class
			,position=1)
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public ModelMap doLogin(
			@Valid @ModelAttribute DoLoginDTO dto,
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model){

		loginManager.doLogin(dto, request, response);

		model.clear();
		model.addAttribute("message","로그인 처리가 완료되었습니다.");

		// HttpServletResponse response 를 받을 경우에는 modelMap return 해야 한다.
		return model;
	}

	@ApiOperation(value = "회원 로그아웃"
			,notes = "로그 아웃 프로세스를 실행한다."
			,response=ResponseResult.class
			,position=1)
	@RequestMapping(value="/doLogout",method=RequestMethod.POST)
	public ModelMap doLogout(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelMap model){

		loginManager.doLogout(response);

		model.clear();
		model.addAttribute("message","로그아웃 처리가 완료되었습니다.");
		// HttpServletResponse response 를 받을 경우에는 modelMap return 해야 한다.
		return model;
	}


	@ApiOperation(value = "로그인 정보 획득"
			,notes = "cookie 에 저장되어 있는 로그인 정보를 획득한다."
			,response=ResponseResult.class
			,position=3)
	@RequestMapping(value="/getLogin",method=RequestMethod.GET)
	public void getLogin(
			HttpServletRequest request,
			ModelMap model){
		String memberId = loginManager.getLoginID(request);

		List<MemberVO> memberList  = null;
		if(!StringUtils.isBlank(memberId)){
			memberList=memberManager.findMemberList(new FindMemberListDTO(memberId));
		} else {
			memberList = new ArrayList<MemberVO>();
		}
		model.clear();
		model.addAttribute(memberList);
	}
}
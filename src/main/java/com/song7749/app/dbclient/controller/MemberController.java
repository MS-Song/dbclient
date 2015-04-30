package com.song7749.app.dbclient.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.song7749.dl.base.ResponseResult;
import com.song7749.dl.member.dto.AddMemberDTO;
import com.song7749.dl.member.service.MemberManager;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * <pre>
 * Class Name : MemberController.java
 * Description : 회원 컨트롤러
 *
 *  Modification Information
 *  Modify Date 		Modifier	Comment
 *  -----------------------------------------------
 *  2015. 4. 27.		song7749	신규작성
 *
 * </pre>
 *
 * @author song7749
 * @since 2015. 4. 27.
 */
@Api(value = "member", description = "회원 관련 기능",position=2)
@Controller
@RequestMapping("/member")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	MemberManager memberManager;

	@ApiOperation(value = "데이터 베이스 서버 리스트 조회"
			,notes = "등록되어 있는 Database 서버 리스트를 조회 한다."
			,response=ResponseResult.class)
@RequestMapping(value="/member",method=RequestMethod.POST)
	public void addMember(
			@ModelAttribute AddMemberDTO dto,
			HttpServletRequest request,
			ModelMap model){
		memberManager.addMember(dto);
	}
}

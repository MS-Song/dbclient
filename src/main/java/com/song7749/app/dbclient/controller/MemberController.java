package com.song7749.app.dbclient.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.song7749.dl.member.dto.FindMemberListDTO;
import com.song7749.dl.member.dto.ModifyMemberDTO;
import com.song7749.dl.member.dto.RemoveMemberDTO;
import com.song7749.dl.member.service.MemberManager;
import com.song7749.dl.member.vo.MemberVO;
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

	@ApiOperation(value = "회원가입"
			,notes = "회원 정보를 등록한다."
			,response=ResponseResult.class
			,position=1)
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public void addMember(
			@Valid @ModelAttribute AddMemberDTO dto,
			HttpServletRequest request,
			ModelMap model){
		memberManager.addMember(dto);

		model.clear();
		model.addAttribute("message", "회원 가입이 완료되었습니다.");
	}

	@ApiOperation(value = "회원수정"
			,notes = "회원 정보를 수정한다."
			,response=ResponseResult.class
			,position=2)
	@RequestMapping(value="/modify",method=RequestMethod.PUT)
	public void modifyMember(
			@ModelAttribute ModifyMemberDTO dto,
			HttpServletRequest request,
			ModelMap model){
		memberManager.modifyMember(dto);

		model.clear();
		model.addAttribute("message", "회원 수정이 완료되었습니다.");
	}

	@ApiOperation(value = "회원삭제"
			,notes = "회원 정보를 삭제한다."
			,response=ResponseResult.class
			,position=3)
	@RequestMapping(value="/remove",method=RequestMethod.DELETE)
	public void removeMember(
			@ModelAttribute RemoveMemberDTO dto,
			HttpServletRequest request,
			ModelMap model){
		memberManager.removeMember(dto);

		model.clear();
		model.addAttribute("message", "회원 정보가 삭제되었습니다.");
	}

	@ApiOperation(value = "회원 리스트 조회"
			,notes = "회원 리스트를 조회 한다."
			,response=MemberVO.class
			,responseContainer="ResponseResult"
			,position=4)
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void listMember(
			@ModelAttribute FindMemberListDTO dto,
			HttpServletRequest request,
			ModelMap model){

		List<MemberVO> list = memberManager.findMemberList(dto);

		model.clear();
		model.addAttribute("memberList",list);
	}
}